package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.ipd.eagle.port.hypothesis.HypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.HypothesisRange;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * A simple selection provider for {@link HypothesisRange#INPUT}. This
 * provider creates a sliding windows of a fixed size and creates selections by
 * using [Top1, Top2, .. Top W], [Top2, Top3, .. Top W-1], ... the confidence of
 * the hypotheses will be used as order (best first).
 *
 * @author Dominik Fuchss
 *
 */
public class TopXSlidingWindow implements ISelectionProvider {

	private int maxHypothesesPerSet;
	private int maxGenerations;

	/**
	 * Create the selection provider.
	 *
	 * @param maxHypothesesPerSet the maximum number of hypotheses in a selection
	 *                            for a hypotheses set
	 * @param maxGenerations      the maximum number of selections for a hypotheses
	 *                            set
	 */
	public TopXSlidingWindow(int maxHypothesesPerSet, int maxGenerations) {
		this.maxHypothesesPerSet = Math.max(1, maxHypothesesPerSet);
		this.maxGenerations = Math.max(1, maxGenerations);
	}

	@Override
	public List<List<IHypothesesSelection>> findSelection(List<IHypothesesSet> hypotheses) {
		List<List<IHypothesesSelection>> result = new ArrayList<>();

		// Create selections with exactly n Hypothesis per hypotheses set
		for (IHypothesesSet group : hypotheses) {
			int n = group.isOnlyOneHypothesisValid() ? 1 : this.maxHypothesesPerSet;

			List<IHypothesis> options = new ArrayList<>(group.getSortedHypotheses());
			Collections.sort(options);

			for (int skip = 0; skip < this.maxGenerations; skip++) {
				List<IHypothesis> selections = new ArrayList<>();
				var o = options.iterator();
				int ctr = skip;
				while (o.hasNext() && --ctr >= 0) {
					o.next();
				}

				while (selections.size() < n && o.hasNext()) {
					selections.add(o.next());
				}
				if (!selections.isEmpty()) {
					var s = new HypothesesSelection(group, selections);
					result.add(List.of(s));
				}
			}
		}
		return result;
	}

}
