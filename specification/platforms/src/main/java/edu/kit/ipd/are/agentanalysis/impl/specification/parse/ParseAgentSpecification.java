package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import edu.kit.ipd.are.agentanalysis.impl.specification.AbstractAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

abstract class ParseAgentSpecification<A extends AbstractAgent> extends AbstractAgentSpecification<A> {
	protected ParseAgentSpecification(A agent) {
		super(agent);
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.PARSE;
	}

}
