package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.List;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * Defines a base class for filters. The selection provider will filter the
 * hypotheses. Then the filter will delegate to another
 * {@link ISelectionProvider}. After that, the filter will create the actual
 * hypotheses based on the results of the other selection provider.
 *
 * @author Dominik Fuchss
 *
 */
public abstract class DecoratorBase implements ISelectionProvider {

	private ISelectionProvider provider;

	/**
	 * Create a new filter by a selection provider for delegation.
	 *
	 * @param provider the selection provider
	 */
	protected DecoratorBase(ISelectionProvider provider) {
		this.provider = provider;
	}

	@Override
	public List<List<IHypothesesSelection>> findSelection(List<IHypothesesSet> hypotheses) {
		List<IHypothesesSet> filtered = this.filterHypotheses(hypotheses);
		List<List<IHypothesesSelection>> rated = this.provider.findSelection(filtered);
		return this.mapToOriginalSets(hypotheses, rated);
	}

	/**
	 * The pre processing: Modify the sets of hypotheses as you want and provide new
	 * sets of hypotheses.
	 *
	 * @param hypotheses the input hypotheses
	 * @return the output hypotheses
	 */
	protected abstract List<IHypothesesSet> filterHypotheses(List<IHypothesesSet> hypotheses);

	/**
	 * The post processing: based on the results of the selection provider generate
	 * the correct hypotheses selections.
	 *
	 * @param original   the original hypotheses for selection
	 * @param selections the hypotheses selections of the provider
	 * @return the correct hypotheses selections
	 */
	protected abstract List<List<IHypothesesSelection>> mapToOriginalSets(List<IHypothesesSet> original, List<List<IHypothesesSelection>> selections);

}
