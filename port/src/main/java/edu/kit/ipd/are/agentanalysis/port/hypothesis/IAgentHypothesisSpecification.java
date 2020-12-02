package edu.kit.ipd.are.agentanalysis.port.hypothesis;

import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

/**
 * Defines the combination of an {@link IAgentSpecification} and an
 * {@link IHypothesesManager}. Also provides
 * {@link #getHypothesesForNonHypothesesExecution(edu.kit.ipd.parse.luna.graph.IGraph)
 * getHypothesesForNonHypothesesExecution(IGraph)} for an
 * {@link IAgentSpecification}.
 *
 * @param <A> the actual {@link AbstractAgent}
 * @author Dominik Fuchss
 *
 */
public interface IAgentHypothesisSpecification<A extends AbstractAgent> extends IAgentSpecification<A>, IHypothesesManager {
	/**
	 * Get the hypotheses range for this agent.
	 *
	 * @return the hypotheses range
	 */
	HypothesisRange getHypothesesRange();
}
