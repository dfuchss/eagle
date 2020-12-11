package edu.kit.ipd.are.agentanalysis.port.hypothesis;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.IDataStructure;

/**
 * Defines a manager for {@link IHypothesis hypothesis}. Normally that would be
 * the agent which generates.
 *
 * @author Dominik Fuchss
 * @param <DS> data structure
 */
public interface IHypothesesManager<DS extends IDataStructure<DS>> {

	/**
	 * Retrieve the "normal" results of an {@link IAgentSpecification} as pseudo
	 * hypotheses.
	 *
	 * @param data the data structure
	 * @return a list of found pseudo hypotheses groups
	 */
	List<IHypothesesSet> getHypothesesForNonHypothesesExecution(DS data);

	/**
	 * Retrieve the hypotheses stored in a data structure
	 *
	 * @param data the data structure
	 * @return a list of found hypotheses groups
	 */
	List<IHypothesesSet> getHypothesesFromDataStructure(DS data);

	/**
	 * Apply a selection of hypotheses to a data structure.
	 *
	 * @param data       the data structure
	 * @param hypotheses the selected hypotheses
	 */
	void applyHypothesesToDataStructure(DS data, List<IHypothesesSelection> hypotheses);

	/**
	 * Get the hypotheses range for this agent.
	 *
	 * @return the hypotheses range
	 */
	HypothesisRange getHypothesesRange();

}
