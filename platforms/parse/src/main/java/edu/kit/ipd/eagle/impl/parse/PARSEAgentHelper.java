package edu.kit.ipd.eagle.impl.parse;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.impl.parse.prepipeline.PrePipelineMode;
import edu.kit.ipd.eagle.impl.parse.specification.AbstractAgentSpecification;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * This class contains some utility methods to execute {@link AbstractAgent
 * AbstractAgents}.
 *
 * @author Dominik Fuchss
 *
 */
public final class PARSEAgentHelper {

	private PARSEAgentHelper() {
		throw new IllegalAccessError();
	}

	/**
	 * Invoke the exec function of {@link AbstractAgent}.
	 *
	 * @param graph the graph
	 * @param agent the agent
	 * @return the result graph (original will not be modified)
	 */
	public static IGraph execute(IGraph graph, AbstractAgent agent) {
		return PARSEAgentHelper.execute(graph, agent, Throwable::printStackTrace);
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
			Method exec = PARSEAgentHelper.findExec(agent.getClass());
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
		return PARSEAgentHelper.findExec(clazz.getSuperclass());
	}

	/**
	 * Find agents which require another {@link PrePipelineMode} than specified.
	 *
	 * @param agents the agents to check
	 * @param ppm    the mode of pre pipeline
	 * @return a list of all invalid agents
	 */
	public static List<AbstractAgentSpecification<? extends AbstractAgent>> findInvalidAgents(Collection<? extends AbstractAgentSpecification<? extends AbstractAgent>> agents, PrePipelineMode ppm) {
		return agents.stream().filter(a -> a.getMode() != ppm).collect(Collectors.toList());
	}

}
