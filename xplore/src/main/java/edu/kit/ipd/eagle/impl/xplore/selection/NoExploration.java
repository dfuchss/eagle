package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.List;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.port.hypothesis.HypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * Simply use the best hypothesis as truth.
 *
 * @author Dominik Fuchss
 *
 */
public class NoExploration implements ISelectionProvider {

	@Override
	public List<List<IHypothesesSelection>> findSelection(List<IHypothesesSet> hypotheses) {
		return List.of( //
				hypotheses.stream() //
						.filter(hs -> !hs.getHypotheses().isEmpty()) //
						.map(hs -> new HypothesesSelection(hs, List.of(hs.getSortedHypotheses().get(0)))) //
						.collect(Collectors.toList()));
	}

}
