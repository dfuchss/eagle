package edu.kit.ipd.are.agentanalysis.impl.specification.indirect;

import edu.kit.ipd.are.agentanalysis.impl.specification.AbstractAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

abstract class IndirectAgentSpecification<A extends AbstractAgent> extends AbstractAgentSpecification<A> {

	protected IndirectAgentSpecification(A agent) {
		super(agent);
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.INDIRECT;
	}

}