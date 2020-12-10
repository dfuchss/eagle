package edu.kit.ipd.are.agentanalysis.impl.parse;

import edu.kit.ipd.are.agentanalysis.port.IAgent;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.graph.IGraph;

public class PARSEAgent implements IAgent<PARSEGraphWrapper> {

	private final AbstractAgent agent;

	public PARSEAgent(AbstractAgent agent) {
		this.agent = agent;
	}

	public AbstractAgent getAgent() {
		return agent;
	}

	@Override
	public PARSEGraphWrapper execute(PARSEGraphWrapper input) {
		IGraph result = PARSEAgentHelper.execute(input.getGraph(), this.agent);
		return new PARSEGraphWrapper(result, input.getPrePipelineMode());
	}
}
