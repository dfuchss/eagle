package edu.kit.ipd.eagle.impl.xplore.rating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.eagle.port.xplore.IPath;
import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;
import edu.kit.ipd.eagle.port.xplore.rating.IRatingFunction;

/**
 * A simple rating function. This rating function normalizes the values of each layer to a [min,max] interval. After
 * that this rating function calculates the scores by using a {@link LayerCombination} and {@link LayerEntryEvaluation}.
 *
 * @author Dominik Fuchss
 *
 */
public class NormalizedAggregate implements IRatingFunction {

	private static final double NO_EVAL = Double.NEGATIVE_INFINITY;

	private HypothesesSelectionFunction selector;
	private LayerEntryEvaluation layerEntryEvaluation;
	private LayerCombination layerCombination;
	private double minMin;
	private double maxMax;

	/**
	 * Create a new normalized aggregate rating function using
	 * {@link HypothesesSelectionFunction#USE_SELECTION_FROM_BEFORE} .
	 *
	 * @param layerEntryEvaluation the used layer evaluation
	 * @param layerCombination     the used layer combination
	 * @param min                  the min value of the normalization (if you are using {@link LayerCombination#MUL} you
	 *                             maybe do not want to use {@code 0}; instead you maybe want to use some small value
	 *                             eps)
	 * @param max                  the max value of the normalization
	 */

	public NormalizedAggregate(LayerEntryEvaluation layerEntryEvaluation, LayerCombination layerCombination, double min, double max) {
		this(HypothesesSelectionFunction.USE_SELECTION_FROM_BEFORE, layerEntryEvaluation, layerCombination, min, max);
	}

	/**
	 * Create a new normalized aggregate rating function.
	 *
	 * @param selector             the selector for hypotheses to analyze
	 * @param layerEntryEvaluation the used layer evaluation
	 * @param layerCombination     the used layer combination
	 * @param min                  the min value of the normalization (if you are using {@link LayerCombination#MUL} you
	 *                             maybe do not want to use {@code 0}; instead you maybe want to use some small value
	 *                             eps)
	 * @param max                  the max value of the normalization
	 */
	public NormalizedAggregate(HypothesesSelectionFunction selector, LayerEntryEvaluation layerEntryEvaluation, LayerCombination layerCombination, double min, double max) {
		this.selector = Objects.requireNonNull(selector);

		this.layerEntryEvaluation = Objects.requireNonNull(layerEntryEvaluation);
		this.layerCombination = Objects.requireNonNull(layerCombination);
		this.minMin = min;
		this.maxMax = max;
	}

	@Override
	public List<Double> ratePaths(List<IPath> pathsWithMetadata) {
		if (pathsWithMetadata.isEmpty()) {
			return List.of();
		}

		List<ILayerEntry[]> paths = pathsWithMetadata.stream().map(IPath::getPath).collect(Collectors.toList());

		// Get longest path ..
		int pathLength = paths.stream().mapToInt(p -> p.length).max().getAsInt();

		double[] mins = new double[pathLength];
		double[] maxs = new double[pathLength];
		Arrays.fill(mins, Double.POSITIVE_INFINITY);
		Arrays.fill(maxs, Double.NEGATIVE_INFINITY);

		for (var path : paths) {
			this.findMinMaxPerEntryPerPath(path, mins, maxs);
		}

		List<Double> normalizedValues = new ArrayList<>();
		for (ILayerEntry[] path : paths) {
			if (path.length != pathLength) {
				if (IRatingFunction.logger.isErrorEnabled()) {
					IRatingFunction.logger.error("Could not evaluate path as length is " + path.length + " instead of " + pathLength + ": " + Arrays.toString(path));
				}
				normalizedValues.add(NormalizedAggregate.NO_EVAL);
				continue;
			}

			Double[] normalizedEntry = this.calculateNormalizedEntry(path, mins, maxs);
			double combined = this.layerCombination.applyAsDouble(Arrays.stream(normalizedEntry));
			if (IRatingFunction.logger.isInfoEnabled()) {
				IRatingFunction.logger.info("Evaluated Path " + Arrays.toString(path) + ": " + combined);
			}
			normalizedValues.add(combined);
		}

		return normalizedValues;
	}

	private Double[] calculateNormalizedEntry(ILayerEntry[] path, double[] mins, double[] maxs) {
		Double[] avgs = new Double[path.length];
		for (int i = 0; i < path.length; i++) {
			double min = mins[i];
			double max = maxs[i];
			double dist = max - min;

			if (dist == 0) {
				avgs[i] = (this.maxMax - this.minMin) / 2;
				continue;
			}

			List<IHypothesis> hypotheses = this.selector.select(path, i);
			if (hypotheses.isEmpty()) {
				avgs[i] = null;
			} else {
				avgs[i] = this.findByFunc(hypotheses, d -> this.layerEntryEvaluation.applyAsDouble(d.map(v -> v - min).map(v -> v / dist).map(this::applyMinMax)));
			}
		}
		return avgs;
	}

	private double applyMinMax(double normalizedValue) {
		return Math.max(this.minMin, Math.min(this.maxMax, normalizedValue));
	}

	private void findMinMaxPerEntryPerPath(ILayerEntry[] path, double[] mins, double[] maxs) {
		for (int i = 0; i < path.length; i++) {
			var hypotheses = path[i].getHypotheses();
			List<IHypothesis> h = hypotheses.stream().flatMap(hs -> hs.getSortedHypotheses().stream()).collect(Collectors.toList());

			if (h.isEmpty()) {
				continue;
			}

			double max = this.findByFunc(h, s -> s.max().getAsDouble());
			double min = this.findByFunc(h, s -> s.min().getAsDouble());
			if (min < mins[i]) {
				mins[i] = min;
			}
			if (max > maxs[i]) {
				maxs[i] = max;
			}
		}
	}

	private double findByFunc(List<IHypothesis> hypotheses, ToDoubleFunction<DoubleStream> function) {
		if (hypotheses == null) {
			return Double.NaN;
		}
		var values = hypotheses.stream().mapToDouble(IHypothesis::getConfidence);
		return function.applyAsDouble(values);
	}

}
