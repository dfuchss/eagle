package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.List;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * Filter hypotheses by confidence.
 *
 * @author Dominik Fuchss
 *
 */
public class FilterByConfidence extends DecoratorBase {

	private final double minConfidence;

	/**
	 * Create a filter for the confidence.
	 *
	 * @param provider      the provider to delegate
	 * @param minConfidence the lower bound for confidence
	 */
	public FilterByConfidence(ISelectionProvider provider, double minConfidence) {
		super(provider);
		this.minConfidence = minConfidence;
	}

	@Override
	protected List<IHypothesesSet> filterHypotheses(List<IHypothesesSet> hypotheses) {
		return hypotheses.stream()
				.map(hs -> hs.withHypotheses(hs.getHypotheses().stream().filter(h -> h.getConfidence() >= minConfidence).collect(Collectors.toList())))
				.filter(hs -> !hs.getHypotheses().isEmpty())
				.collect(Collectors.toList());
	}

	@Override
	protected List<List<IHypothesesSelection>> mapToOriginalSets(List<IHypothesesSet> original, List<List<IHypothesesSelection>> selections) {
		return selections;
	}

}
