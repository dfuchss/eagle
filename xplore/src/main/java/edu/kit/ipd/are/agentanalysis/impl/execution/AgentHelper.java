package edu.kit.ipd.are.agentanalysis.impl.execution;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.are.agentanalysis.port.AgentAnalysisConfiguration;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * This class contains some utility methods to execute {@link AbstractAgent
 * AbstractAgents}.
 *
 * @author Dominik Fuchss
 *
 */
public final class AgentHelper {

	private static final Logger logger = LoggerFactory.getLogger(AgentHelper.class);

	private AgentHelper() {
		throw new IllegalAccessError();
	}

	/**
	 * Get a correct execution order of a collection of agents.
	 *
	 * @param agentsToRun a collection of agents
	 * @return a ordered list of the agents or {@code null} if no valid order exists
	 */
	public static List<IAgentSpecification<?>> findAgentOrder(Collection<? extends IAgentSpecification<?>> agentsToRun) {
		List<IAgentSpecification<?>> order = new ArrayList<>();

		List<InformationId> provided = new ArrayList<>();
		List<IAgentSpecification<?>> specsToRun = new ArrayList<>(agentsToRun);

		while (!specsToRun.isEmpty()) {
			IAgentSpecification<?> next = AgentHelper.findNext(specsToRun, provided);
			if (next == null) {
				if (AgentHelper.logger.isErrorEnabled()) {
					AgentHelper.logger.error("Provided Information: " + provided + " | Remaining Agents: " + specsToRun);
				}
				return null;
			}
			order.add(next);
			specsToRun.remove(next);
			provided.addAll(next.getProvideIds());
		}
		return order;
	}

	private static IAgentSpecification<?> findNext(List<IAgentSpecification<?>> specsToRun, List<InformationId> alreadyRun) {
		for (IAgentSpecification<?> a : specsToRun) {
			if (alreadyRun.containsAll(a.getRequiresIds())) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Invoke the exec function of {@link AbstractAgent}.
	 *
	 * @param graph the graph
	 * @param agent the agent
	 * @return the result graph (original will not be modified)
	 */
	public static IGraph execute(IGraph graph, AbstractAgent agent) {
		return AgentHelper.execute(graph, agent, Throwable::printStackTrace);
	}

	/**
	 * Invoke the exec function of {@link AbstractAgent}.
	 *
	 * @param graph       the graph
	 * @param agent       the agent
	 * @param onException a consumer for possible exceptions
	 * @return the result graph (original will not be modified)
	 */
	public static IGraph execute(IGraph graph, AbstractAgent agent, Consumer<Throwable> onException) {
		try {
			IGraph result = graph.clone();
			agent.setGraph(result);
			agent.init();
			Method exec = AgentHelper.findExec(agent.getClass());
			if (exec == null) {
				throw new UnsupportedOperationException("exec() method was not found");
			}
			exec.setAccessible(true);
			exec.invoke(agent);
			return result;
		} catch (Exception e) {
			if (onException != null) {
				onException.accept(e);
			}
			return null;
		}
	}

	private static Method findExec(Class<?> clazz) {
		if (clazz == null || clazz == Object.class) {
			return null;
		}
		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			if ("exec".equals(m.getName()) && m.getParameterCount() == 0) {
				return m;
			}
		}
		return AgentHelper.findExec(clazz.getSuperclass());
	}

	/**
	 * Find agents which require another {@link PrePipelineMode} than specified.
	 *
	 * @param agents the agents to check
	 * @param ppm    the mode of pre pipeline
	 * @return a list of all invalid agents
	 * @see AgentAnalysisConfiguration#setOverridePrePipelineRestrictions(boolean)
	 */
	public static List<IAgentSpecification<?>> findInvalidAgents(Collection<IAgentSpecification<?>> agents, PrePipelineMode ppm) {
		if (AgentAnalysisConfiguration.isOverridePrePiplineRestricitions()) {
			return List.of();
		}
		return agents.stream().filter(a -> a.getMode() != ppm).collect(Collectors.toList());
	}

}
