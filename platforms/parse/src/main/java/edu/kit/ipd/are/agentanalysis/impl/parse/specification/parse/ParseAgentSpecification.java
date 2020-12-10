package edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse;

import edu.kit.ipd.are.agentanalysis.impl.parse.specification.AbstractAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

public abstract class ParseAgentSpecification<A extends AbstractAgent> extends AbstractAgentSpecification<A> {
	protected ParseAgentSpecification(A agent) {
		super(agent);
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.PARSE;
	}

}
