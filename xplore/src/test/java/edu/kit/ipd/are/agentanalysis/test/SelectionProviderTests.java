package edu.kit.ipd.are.agentanalysis.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.kit.ipd.are.agentanalysis.impl.xplore.selection.AllCombinationsIfOnlyOneValid;
import edu.kit.ipd.are.agentanalysis.impl.xplore.selection.RandomSelectionIfOnlyOneValid;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.xplore.dto.HypothesesSetDTO;
import edu.kit.ipd.are.agentanalysis.port.xplore.dto.HypothesisDTO;
import edu.kit.ipd.are.agentanalysis.port.xplore.selection.ISelectionProvider;

/**
 * Some tests for {@link ISelectionProvider Selection Providers}
 *
 * @author Dominik Fuchss
 *
 */
public class SelectionProviderTests {
	private HypothesisDTO hyp1;
	private HypothesisDTO hyp2;
	private HypothesesSetDTO set1;
	private HypothesesSetDTO set2;

	/**
	 * Create some hypotheses for testing.
	 */
	@Before
	public void createSomeHypotheses() {
		this.hyp1 = new HypothesisDTO();
		this.hyp1.setConfidence(1);
		this.hyp1.setValue("val1");

		this.hyp2 = new HypothesisDTO();
		this.hyp2.setConfidence(2);
		this.hyp2.setValue("val2");

		this.set1 = new HypothesesSetDTO();
		this.set1.setHypotheses(List.of(this.hyp1));
		this.set1.setHypothesesRange(HypothesisRange.NODE);
		this.set1.setOnlyOneHypothesisValid(true);

		this.set2 = new HypothesesSetDTO();
		this.set2.setHypotheses(List.of(this.hyp1, this.hyp2));
		this.set2.setHypothesesRange(HypothesisRange.NODE);
		this.set2.setOnlyOneHypothesisValid(true);
	}

	/**
	 * Check selection mechanisms of {@link AllCombinationsIfOnlyOneValid}.
	 */
	@Test
	public void checkAllCombinationsIfOnlyOneValid() {
		AllCombinationsIfOnlyOneValid acioov = new AllCombinationsIfOnlyOneValid();
		var selections = acioov.findSelection(List.of(this.set1, this.set2));
		this.checkSelectionValid(selections);
		Assert.assertEquals(2, selections.size());

		selections = acioov.findSelection(List.of(this.set2, this.set2));
		this.checkSelectionValid(selections);
		Assert.assertEquals(4, selections.size());

		selections = acioov.findSelection(List.of(this.set2, this.set2, this.set2));
		this.checkSelectionValid(selections);
		Assert.assertEquals(8, selections.size());
	}

	/**
	 * Check selection mechanisms of {@link RandomSelectionIfOnlyOneValid}.
	 */
	@Test
	public void checkRandomSelectionIfOnlyOneValid() {
		RandomSelectionIfOnlyOneValid rsioov = new RandomSelectionIfOnlyOneValid(-1); // Default full exploration ..
		var selections = rsioov.findSelection(List.of(this.set2, this.set2, this.set2));
		this.checkSelectionValid(selections);
		Assert.assertEquals(8, selections.size());

		rsioov = new RandomSelectionIfOnlyOneValid(0.5); // 50 % of paths
		selections = rsioov.findSelection(List.of(this.set2, this.set2, this.set2));
		this.checkSelectionValid(selections);
		Assert.assertEquals(4, selections.size());

		rsioov = new RandomSelectionIfOnlyOneValid(3); // 3 max
		selections = rsioov.findSelection(List.of(this.set2, this.set2, this.set2));
		this.checkSelectionValid(selections);
		Assert.assertEquals(3, selections.size());
	}

	private void checkSelectionValid(List<List<IHypothesesSelection>> selections) {
		for (var selection : selections) {
			for (var selected : selection) {
				Assert.assertTrue(List.of(this.set1, this.set2).contains(selected.getAllHypotheses()));
				Assert.assertTrue(selected.getSelectedHypotheses().stream().allMatch(h -> List.of(this.hyp1, this.hyp2).contains(h)));
			}
		}

	}

}
