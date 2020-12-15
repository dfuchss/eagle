package edu.kit.ipd.are.agentanalysis.port.hypothesis;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.IAgent;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.IDataStructure;

/**
 * Defines the extension of an {@link IAgentSpecification} that is suitable for
 * dealing with hypotheses of agents.
 *
 * @param <A>  the actual agent
 * @param <DS> the actual data structure
 * @author Dominik Fuchss
 *
 */
public interface IAgentHypothesisSpecification<A extends IAgent<DS>, DS extends IDataStructure<DS>> extends IAgentSpecification<A, DS> {
	/**
	 * Just some default number for the amount of hypotheses to be generated.
	 */
	int DEFAULT_HYPOTHESES = 3;

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
