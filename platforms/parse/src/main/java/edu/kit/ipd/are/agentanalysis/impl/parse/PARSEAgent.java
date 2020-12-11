package edu.kit.ipd.are.agentanalysis.impl.parse;

import edu.kit.ipd.are.agentanalysis.port.IAgent;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * The adapter that adapts an {@link AbstractAgent} to an {@link IAgent}.
 *
 * @author Dominik Fuchss
 *
 */
public final class PARSEAgent implements IAgent<PARSEGraphWrapper> {

	private final AbstractAgent agent;

	/**
	 * Create a {@link IAgent} based on an {@link AbstractAgent}.
	 *
	 * @param agent the instance of the agent
	 */
	public PARSEAgent(AbstractAgent agent) {
		this.agent = agent;
	}

	@Override
	public String getName() {
		return this.agent.getId();
	}

	@Override
	public PARSEGraphWrapper execute(PARSEGraphWrapper input) {
		IGraph result = PARSEAgentHelper.execute(input.getGraph(), this.agent);
		return new PARSEGraphWrapper(result, input.getText(), input.getPrePipelineMode());
	}
}
