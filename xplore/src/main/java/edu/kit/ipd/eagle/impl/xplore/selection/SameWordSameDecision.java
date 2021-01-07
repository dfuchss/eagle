package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.port.hypothesis.BasicHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.HypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.HypothesisRange;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * This decorator can be used to ensure that hypotheses with
 * {@link HypothesisRange#ELEMENT} which belong to the same word get the same
 * selection in a {@link IHypothesesSelection}.
 *
 * @author Dominik Fuchss
 *
 */
public class SameWordSameDecision extends DecoratorBase {
	/**
	 * Create the decorator.
	 *
	 * @param provider the actual selection provider for delegation
	 */
	public SameWordSameDecision(ISelectionProvider provider) {
		super(provider);
	}

	@Override
	protected List<IHypothesesSet> filterHypotheses(List<IHypothesesSet> hypotheses) {
		var grouped = hypotheses.stream().collect(Collectors.groupingBy(h -> h.getElementOfHypotheses() == null ? "" : h.getElementOfHypotheses().toLowerCase()));
		List<IHypothesesSet> newHypotheses = new ArrayList<>();

		for (var group : grouped.entrySet()) {
			String word = group.getKey();
			var groupHypothesesSets = group.getValue();
			if (word == null) {
				newHypotheses.addAll(groupHypothesesSets);
			} else {
				IHypothesesSet joinedHypotheses = this.createJoinedHypotheses(groupHypothesesSets);
				newHypotheses.add(joinedHypotheses);
			}
		}
		return newHypotheses;
	}

	private IHypothesesSet createJoinedHypotheses(List<IHypothesesSet> groupHypothesesSets) {
		boolean isOnlyOneValid = groupHypothesesSets.get(0).isOnlyOneHypothesisValid();
		HypothesisRange range = groupHypothesesSets.get(0).getHypothesesRange();
		List<IHypothesis> hypotheses = new ArrayList<>();
		for (IHypothesesSet set : groupHypothesesSets) {
			for (IHypothesis hyp : set.getHypotheses()) {
				this.addHypothesis(hypotheses, hyp);
			}
		}

		BasicHypothesesSet bs = new BasicHypothesesSet("JoinedHypotheses from " + groupHypothesesSets, range, hypotheses, isOnlyOneValid);
		return bs;
	}

	private void addHypothesis(List<IHypothesis> hypotheses, IHypothesis hyp) {
		IHypothesis match = hypotheses.stream().filter(h -> Objects.equals(h.getValue(), hyp.getValue())).findFirst().orElse(null);
		if (match == null) {
			hypotheses.add(hyp);
		} else if (match.getConfidence() < hyp.getConfidence()) {
			hypotheses.remove(match);
			hypotheses.add(hyp);
		}

	}

	@Override
	protected List<List<IHypothesesSelection>> mapToOriginalSets(List<IHypothesesSet> original, List<List<IHypothesesSelection>> selections) {
		var grouped = original.stream().collect(Collectors.groupingBy(h -> h.getElementOfHypotheses() == null ? "" : h.getElementOfHypotheses().toLowerCase()));
		List<List<IHypothesesSelection>> result = new ArrayList<>();
		for (List<IHypothesesSelection> selection : selections) {
			var convertedSelection = this.createSelections(grouped, selection);
			result.add(convertedSelection);
		}

		return result;
	}

	private List<IHypothesesSelection> createSelections(Map<String, List<IHypothesesSet>> grouped, List<IHypothesesSelection> selections) {
		List<IHypothesesSelection> result = new ArrayList<>();

		for (IHypothesesSelection selection : selections) {
			IHypothesis someHypothesis = selection.getAllHypotheses().getHypotheses().get(0);
			var groupEntry = grouped.entrySet().stream().filter(e -> e.getValue().stream().anyMatch(hs -> hs.getHypotheses().contains(someHypothesis))).findFirst().orElse(null);
			if (groupEntry == null) {
				continue;
			}
			if (groupEntry.getKey() == null) {
				result.add(selection);
			} else {
				for (var hypoSet : groupEntry.getValue()) {
					IHypothesesSelection hs = new HypothesesSelection(hypoSet, selection.getSelectedHypotheses());
					result.add(hs);
				}
			}
		}

		return result;
	}

}
