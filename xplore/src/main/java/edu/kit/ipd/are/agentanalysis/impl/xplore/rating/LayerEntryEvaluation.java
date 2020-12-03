package edu.kit.ipd.are.agentanalysis.impl.xplore.rating;

import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;

/**
 * Defines the different evaluation functions for scores of the hypotheses in a
 * {@link ILayerEntry}. So these methods evaluate the scores of the
 * {@link IHypothesesSet} or {@link IHypothesesSelection} in an
 * {@link ILayerEntry}.
 *
 * @author Dominik Fuchss
 *
 */
public enum LayerEntryEvaluation implements ToDoubleFunction<DoubleStream> {
	/**
	 * Use the median of the scores as final score.
	 */
	MEDIAN(s -> {
		var sortedNormalized = s.sorted().mapToObj(dv -> dv).collect(Collectors.toList());
		int sizeHalf = sortedNormalized.size() / 2;

		if (sortedNormalized.size() % 2 == 0) {
			return (sortedNormalized.get(sizeHalf) + sortedNormalized.get(sizeHalf - 1)) / 2;
		} else {
			return sortedNormalized.get(sizeHalf);
		}
	}),

	/**
	 * Use the standard derivation of the scores as final score.
	 */
	SIGMA(s -> {
		var normalized = s.mapToObj(dv -> dv).collect(Collectors.toList());
		int n = normalized.size();
		double m = normalized.stream().mapToDouble(d -> d).average().getAsDouble();
		double s2 = normalized.stream().mapToDouble(d -> (d - m) * (d - m)).sum() / n;
		return Math.sqrt(s2);
	}),

	/**
	 * Use the average of the scores as final score.
	 */
	AVERAGE(s -> s.average().getAsDouble()),

	/**
	 * Use the max value of the scores as final score.
	 */
	MAX(s -> s.max().getAsDouble()),

	/**
	 * Use the min value of the scores as final score.
	 */
	MIN(s -> s.min().getAsDouble());

	private ToDoubleFunction<DoubleStream> function;

	LayerEntryEvaluation(ToDoubleFunction<DoubleStream> function) {
		this.function = function;
	}

	@Override
	public double applyAsDouble(DoubleStream value) {
		return this.function.applyAsDouble(value);
	}
}