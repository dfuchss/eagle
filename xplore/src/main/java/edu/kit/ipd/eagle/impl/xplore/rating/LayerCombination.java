package edu.kit.ipd.eagle.impl.xplore.rating;

import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;

import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;

/**
 * Defines the different combination functions for scores of a way. So these
 * methods combine the scores of the {@link ILayerEntry ILayerEntries} generated
 * by {@link LayerEntryEvaluation} to a single score.
 *
 * @author Dominik Fuchss
 *
 */
public enum LayerCombination implements ToDoubleFunction<DoubleStream> {
	/**
	 * Combine by simple build a sum.
	 */
	ADD(DoubleStream::sum),
	/**
	 * Combine by simple build a product.
	 */
	MUL(s -> s.reduce(1, (a, b) -> a * b));

	private ToDoubleFunction<DoubleStream> function;

	LayerCombination(ToDoubleFunction<DoubleStream> function) {
		this.function = function;
	}

	@Override
	public double applyAsDouble(DoubleStream value) {
		return this.function.applyAsDouble(value);
	}

}