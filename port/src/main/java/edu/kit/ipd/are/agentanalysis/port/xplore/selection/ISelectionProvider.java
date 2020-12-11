package edu.kit.ipd.are.agentanalysis.port.xplore.selection;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;

/**
 * Defines a provider for {@link IHypothesesSelection Selections}. The provider
 * takes a list of {@link IHypothesesSet IHypothesesSets} and provides multiple
 * groups of possible selections.
 *
 * @author Dominik Fuchss
 *
 */
public interface ISelectionProvider {
	/**
	 * The default logger for the selection providers.
	 */
	Logger logger = LoggerFactory.getLogger(ISelectionProvider.class);

	/**
	 * Find possible groups of selections.
	 *
	 * @param hypotheses the hypotheses
	 * @return multiple groups of selections as a list
	 */
	List<List<IHypothesesSelection>> findSelection(List<IHypothesesSet> hypotheses);

}
