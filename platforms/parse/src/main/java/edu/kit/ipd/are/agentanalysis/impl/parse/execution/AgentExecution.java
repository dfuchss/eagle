package edu.kit.ipd.are.agentanalysis.impl.parse.execution;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.ipd.are.agentanalysis.impl.parse.GraphUtils;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgent;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgentHelper;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.port.AgentHelper;
import edu.kit.ipd.are.agentanalysis.port.IAgentExecution;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * Defines a basic realization of an {@link IAgentExecution}.
 *
 * @author Dominik Fuchss
 *
 */
public class AgentExecution implements IAgentExecution<PARSEAgent, PARSEGraphWrapper> {

	private Set<IAgentSpecification<? extends PARSEAgent, PARSEGraphWrapper>> agents = new HashSet<>();

	@Override
	public void loadAgent(IAgentSpecification<? extends PARSEAgent, PARSEGraphWrapper> agentSpec) {
		this.agents.add(agentSpec);
	}

	@Override
	public void unloadAgents() {
		this.agents.clear();
	}

	@Override
	public PARSEGraphWrapper execute(PARSEGraphWrapper input) {
		IGraph graph = input.getGraph();
		PrePipelineMode ppm = input.getPrePipelineMode();

		// Check PPM
		List<IAgentSpecification<? extends PARSEAgent, PARSEGraphWrapper>> invalidAgents = PARSEAgentHelper.findInvalidAgents(this.agents, ppm);
		if (!invalidAgents.isEmpty()) {
			if (IAgentExecution.logger.isErrorEnabled()) {
				IAgentExecution.logger.error("Agent(s) " + invalidAgents + " are no valid agents for PrePipeline " + ppm);
			}
			return null;
		}

		// Execute Agents ..
		List<IAgentSpecification<? extends PARSEAgent, PARSEGraphWrapper>> specsToRun = AgentHelper.findAgentOrder(this.agents);
		if (specsToRun == null) {
			return null;
		}

		for (IAgentSpecification<? extends PARSEAgent, PARSEGraphWrapper> next : specsToRun) {
			if (IAgentExecution.logger.isDebugEnabled()) {
				IAgentExecution.logger.debug("Executing " + next);
			}

			var nextGraph = PARSEAgentHelper.execute(graph, next.getAgentInstance().getAgent());
			if (nextGraph == null) {
				if (IAgentExecution.logger.isErrorEnabled()) {
					IAgentExecution.logger.error("Failed to execute " + next);
				}
				return null;
			}
			graph = nextGraph;

			if (IAgentExecution.logger.isDebugEnabled()) {
				IAgentExecution.logger.debug("After " + next.getAgentInstance().getClass().getSimpleName() + " " + GraphUtils.getStats(graph));
			}
		}
		return new PARSEGraphWrapper(graph, ppm);
	}

}
