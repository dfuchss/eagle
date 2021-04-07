package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.port.hypothesis.HypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;

/**
 * Randomly selects a certain amount from {@link FullExploration}. <b>Randomness is fixed by default! Use
 * {@link #realPseudoRandom()} to unlock real pseudo randomness!</b>
 *
 * @author Dominik Fuchss
 *
 */
public class RandomHypothesis extends FullExploration {
	private Random random = new Random(42);

	private int maxHypothesesSelections;
	private double maxRatioOfSelectedHypotheses;

	private boolean useFirst;

	/**
	 * Specify explicit max amount of hypotheses selections.
	 *
	 * @param maxHypothesesSelections the max amount (all values less than 1 indicate no limit)
	 * @param useFirst                indicates whether the all-best hypotheses shall be included forcefully
	 */
	public RandomHypothesis(int maxHypothesesSelections, boolean useFirst) {
		this(maxHypothesesSelections, -1, useFirst);
	}

	/**
	 * Specify a explicit ratio of hypotheses selections to be chosen.
	 *
	 * @param maxRatioOfSelectedHypotheses the ratio within (0,1] (all values less equal 0 indicates no limit)
	 * @param useFirst                     indicates whether the all-best hypotheses shall be included forcefully
	 */
	public RandomHypothesis(double maxRatioOfSelectedHypotheses, boolean useFirst) {
		this(-1, maxRatioOfSelectedHypotheses, useFirst);
	}

	/**
	 * Specify explicit max amount of hypotheses selections and a explicit ratio of hypotheses selections to be chosen
	 *
	 * @param maxHypothesesSelections      the max amount (all values less than 1 indicate no limit for max amount
	 *                                     check)
	 * @param maxRatioOfSelectedHypotheses the ratio within (0,1] (all values less equal 0 indicates no limit for ratio)
	 * @param useFirst                     indicates whether the all-best hypotheses shall be included forcefully
	 */
	public RandomHypothesis(int maxHypothesesSelections, double maxRatioOfSelectedHypotheses, boolean useFirst) {
		this.useFirst = useFirst;
		if (maxHypothesesSelections < 1) {
			this.maxHypothesesSelections = -1;
		} else {
			this.maxHypothesesSelections = maxHypothesesSelections;
		}

		if (maxRatioOfSelectedHypotheses <= 0) {
			this.maxRatioOfSelectedHypotheses = -1;
		} else {
			if (maxRatioOfSelectedHypotheses > 1) {
				throw new IllegalArgumentException("maxRatioOfSelectedHypotheses can't be > 1");
			}
			this.maxRatioOfSelectedHypotheses = maxRatioOfSelectedHypotheses;
		}
	}

	@Override
	public List<List<IHypothesesSelection>> findSelection(List<IHypothesesSet> hypotheses) {
		// Define a "large" max value.
		long max = (long) 1E8;
		// Indicates whether we can create all combinations to delete randomly
		boolean useDeleteStrategy = true;

		long size = 1;
		for (IHypothesesSet set : hypotheses) {
			size *= set.getHypotheses().size();
			if (size >= max) {
				useDeleteStrategy = false;
				size = max;
				break;
			}
		}

		if (size < max) {
			max = size;
		}

		if (this.maxHypothesesSelections > 0) {
			max = Math.min(max, this.maxHypothesesSelections);
		}

		if (this.maxRatioOfSelectedHypotheses > 0) {
			int amountByRatio = (int) Math.ceil(this.maxRatioOfSelectedHypotheses * size);
			max = Math.min(max, amountByRatio);
		}

		List<List<IHypothesesSelection>> selections = createSelection(hypotheses, (int) max, useDeleteStrategy);
		return selections;
	}

	private List<List<IHypothesesSelection>> createSelection(List<IHypothesesSet> hypotheses, int max, boolean useDeleteStrategy) {
		if (useDeleteStrategy) {
			List<List<IHypothesesSelection>> selections = super.findSelection(hypotheses);
			while (selections.size() > max) {
				int idx = this.random.nextInt(selections.size());
				if (useFirst && idx == 0) {
					continue;
				}
				selections.remove(idx);
			}
			return selections;
		}

		List<Integer> sizes = hypotheses.stream().map(h -> h.getSortedHypotheses().size()).collect(Collectors.toList());
		List<List<IHypothesesSelection>> result = new ArrayList<>();
		Set<String> taken = new HashSet<>();

		// Current Indices
		int[] idx = new int[sizes.size()];

		while (result.size() < max) {
			if (!(useFirst && result.isEmpty())) {
				randomIdx(idx, sizes);
			}
			if (!taken.add(Arrays.toString(idx))) {
				logger.debug("Collision in randomness .. skip ..");
				continue;
			}
			List<IHypothesesSelection> selection = this.generate(hypotheses, idx);
			result.add(selection);
		}

		return result;
	}

	private void randomIdx(int[] idx, List<Integer> sizes) {
		for (int i = 0; i < idx.length; i++) {
			idx[i] = random.nextInt(sizes.get(i));
		}

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

	/**
	 * Set the random source to {@code new Random()}.
	 */
	public void realPseudoRandom() {
		this.random = new Random();
	}
}
