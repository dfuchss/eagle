package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.List;
import java.util.Random;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;

/**
 * Randomly selects a certain amount from {@link FullExploration}.
 * <b>Randomness is fixed by default! Use {@link #realPseudoRandom()} to unlock
 * real pseudo randomness!</b>
 *
 * @author Dominik Fuchss
 *
 */
public class RandomHypothesis extends FullExploration {
	private Random random = new Random(42);

	private int maxHypothesesSelections;
	private double maxRatioOfSelectedHypotheses;

	/**
	 * Specify explicit max amount of hypotheses selections.
	 *
	 * @param maxHypothesesSelections the max amount (all values less than 1
	 *                                indicate no limit)
	 */
	public RandomHypothesis(int maxHypothesesSelections) {
		this(maxHypothesesSelections, -1);
	}

	/**
	 * Specify a explicit ratio of hypotheses selections to be chosen.
	 *
	 * @param maxRatioOfSelectedHypotheses the ratio within (0,1] (all values less
	 *                                     equal 0 indicates no limit)
	 */
	public RandomHypothesis(double maxRatioOfSelectedHypotheses) {
		this(-1, maxRatioOfSelectedHypotheses);
	}

	/**
	 * Specify explicit max amount of hypotheses selections and a explicit ratio of
	 * hypotheses selections to be chosen
	 *
	 * @param maxHypothesesSelections      the max amount (all values less than 1
	 *                                     indicate no limit for max amount check)
	 * @param maxRatioOfSelectedHypotheses the ratio within (0,1] (all values less
	 *                                     equal 0 indicates no limit for ratio)
	 */
	public RandomHypothesis(int maxHypothesesSelections, double maxRatioOfSelectedHypotheses) {
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
		List<List<IHypothesesSelection>> selections = super.findSelection(hypotheses);
		int max = selections.size();
		if (this.maxHypothesesSelections > 0) {
			max = Math.min(max, this.maxHypothesesSelections);
		}

		if (this.maxRatioOfSelectedHypotheses > 0) {
			int amountByRatio = (int) Math.ceil(this.maxRatioOfSelectedHypotheses * selections.size());
			max = Math.min(max, amountByRatio);
		}

		while (selections.size() > max) {
			selections.remove(this.random.nextInt(selections.size()));
		}
		return selections;
	}

	/**
	 * Set the random source to {@code new Random()}.
	 */
	public void realPseudoRandom() {
		this.random = new Random();
	}
}
