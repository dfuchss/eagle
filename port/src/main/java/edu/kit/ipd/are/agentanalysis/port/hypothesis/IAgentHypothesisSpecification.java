package edu.kit.ipd.are.agentanalysis.port.hypothesis;

import edu.kit.ipd.are.agentanalysis.port.IAgent;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.IDataStructure;

/**
 * Defines the combination of an {@link IAgentSpecification} and an
 * {@link IHypothesesManager}. Also provides
 * {@link #getHypothesesForNonHypothesesExecution(Object)
 * getHypothesesForNonHypothesesExecution(DS)} for an
 * {@link IAgentSpecification}.
 *
 * @param <A>  the actual agent
 * @param <DS> the actual data structure
 * @author Dominik Fuchss
 * 
 */
public interface IAgentHypothesisSpecification<A extends IAgent<DS>, DS extends IDataStructure<DS>> extends IAgentSpecification<A, DS>, IHypothesesManager<DS> {
	/**
	 * Get the hypotheses range for this agent.
	 *
	 * @return the hypotheses range
	 */
	HypothesisRange getHypothesesRange();
}
