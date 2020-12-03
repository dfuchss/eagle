package edu.kit.ipd.are.agentanalysis.impl.execution;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.kit.ipd.are.agentanalysis.port.EnhancedGraph;
import edu.kit.ipd.are.agentanalysis.port.IAgentExecution;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.are.agentanalysis.port.util.GraphUtils;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * Defines a basic realization of an {@link IAgentExecution}.
 *
 * @author Dominik Fuchss
 *
 */
public class AgentExecution implements IAgentExecution {

	private Set<IAgentSpecification<?>> agents = new HashSet<>();

	@Override
	public void loadAgents(IAgentSpecification<?>... agentSpecs) {
		this.agents.addAll(Arrays.asList(agentSpecs));
	}

	@Override
	public void unloadAgents() {
		this.agents.clear();
	}

	@Override
	public IGraph execute(EnhancedGraph input) {
		IGraph graph = input.getGraph();
		PrePipelineMode ppm = input.getPrePipelineMode();

		// Check PPM
		List<IAgentSpecification<?>> invalidAgents = AgentHelper.findInvalidAgents(this.agents, ppm);
		if (!invalidAgents.isEmpty()) {
			if (IAgentExecution.logger.isErrorEnabled()) {
				IAgentExecution.logger.error("Agent(s) " + invalidAgents + " are no valid agents for PrePipeline " + ppm);
			}
			return null;
		}

		// Execute Agents ..
		List<IAgentSpecification<?>> specsToRun = AgentHelper.findAgentOrder(this.agents);
		if (specsToRun == null) {
			return null;
		}

		for (IAgentSpecification<?> next : specsToRun) {
			if (IAgentExecution.logger.isDebugEnabled()) {
				IAgentExecution.logger.debug("Executing " + next);
			}

			var nextGraph = AgentHelper.execute(graph, next.getAgentInstance());
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
		return graph;
	}

}
