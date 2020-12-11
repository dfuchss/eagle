package edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse;

import edu.kit.ipd.are.agentanalysis.impl.parse.prepipeline.PrePipelineMode;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.AbstractAgentSpecification;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

/**
 * The base class for the agent specification in the PARSE context.
 *
 * @author Dominik Fuchss
 *
 * @param <A> the type of agent
 */
public abstract class ParseAgentSpecification<A extends AbstractAgent> extends AbstractAgentSpecification<A> {

	/**
	 * Create specification of an indirect agent based on one instance.
	 *
	 * @param agent the instance
	 */
	protected ParseAgentSpecification(A agent) {
		super(agent);
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.PARSE;
	}

}
