package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.List;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * This selection provider provides <b>all</b> combinations of the {@link IHypothesis} in {@link IHypothesesSet
 * IHypothesesSets} for sets with {@link IHypothesesSet#isOnlyOneHypothesisValid()} is {@code true}.
 *
 * @author Dominik Fuchss
 *
 */
public class FullExploration extends AbstractCombinatoricExploration implements ISelectionProvider {

	@Override
	public List<List<IHypothesesSelection>> findSelection(List<IHypothesesSet> hypotheses) {
		return generateSelections(hypotheses);
	}

}
