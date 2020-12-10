package edu.kit.ipd.are.agentanalysis.impl.parse.specification.indirect;

import edu.kit.ipd.are.agentanalysis.impl.parse.specification.AbstractAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

public abstract class IndirectAgentSpecification<A extends AbstractAgent> extends AbstractAgentSpecification<A> {

	protected IndirectAgentSpecification(A agent) {
		super(agent);
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.INDIRECT;
	}

}