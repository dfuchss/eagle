package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.port.hypothesis.HypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * Abstract base class that provides functionality to generate all combinations for a certain set of hypotheses sets.
 *
 * @author Dominik Fuchss
 *
 */
abstract class AbstractCombinatoricExploration implements ISelectionProvider {
	/**
	 * Generate all combinations as {@link List} of {@link IHypothesesSelection}.
	 *
	 * @param  hypotheses the initial hypotheses sets
	 * @return            the combinations
	 */
	protected List<List<IHypothesesSelection>> generateSelections(List<IHypothesesSet> hypotheses) {
		this.checkHypothesesOnlyOneValid(hypotheses);
		List<Integer> sizes = hypotheses.stream().map(h -> h.getSortedHypotheses().size()).collect(Collectors.toList());
		List<List<IHypothesesSelection>> result = new ArrayList<>();
		// Current Indices
		int[] idx = new int[sizes.size()];

		while (!isLastIteration(sizes, idx)) {
			List<IHypothesesSelection> selection = this.generate(hypotheses, idx);
			result.add(selection);
			nextIteration(sizes, idx);
		}

		// Last Iteration
		List<IHypothesesSelection> selection = this.generate(hypotheses, idx);
		result.add(selection);

		return result;
	}

	private List<IHypothesesSelection> generate(List<IHypothesesSet> hypotheses, int[] idx) {
		List<IHypothesesSelection> result = new ArrayList<>();
		for (int i = 0; i < idx.length; i++) {
			IHypothesis selectedHypothesis = hypotheses.get(i).getSortedHypotheses().get(idx[i]);
			HypothesesSelection selection = new HypothesesSelection(hypotheses.get(i), List.of(selectedHypothesis));
			result.add(selection);
		}
		return result;
	}

	private static void nextIteration(List<Integer> sizes, int[] idx) {
		for (int digit = idx.length - 1; digit >= 0; digit--) {
			if (idx[digit] == sizes.get(digit) - 1) {
				idx[digit] = 0;
			} else {
				idx[digit]++;
				break;
			}
		}
	}

	private static boolean isLastIteration(List<Integer> sizes, int[] idx) {
		for (int i = 0; i < idx.length; i++) {
			if (idx[i] != sizes.get(i) - 1) {
				return false;
			}
		}

		return true;
	}

	private void checkHypothesesOnlyOneValid(List<IHypothesesSet> hypotheses) {
		for (var hyp : hypotheses) {
			if (!hyp.isOnlyOneHypothesisValid()) {
				throw new IllegalArgumentException("Invalid hypotheses set found .. only one hypothesis can be valid!");
			}
		}

	}
}
