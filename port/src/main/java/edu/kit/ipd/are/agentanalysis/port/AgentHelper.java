package edu.kit.ipd.are.agentanalysis.port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some helper methods to check whether agents have an order.
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
	 * @param <A>         the type of agent
	 * @param <DS>        the type of data structure
	 * @param agentsToRun a collection of agents
	 * @return a ordered list of the agents or {@code null} if no valid order exists
	 */
	public static <A extends IAgent<DS>, DS extends IDataStructure<DS>> List<IAgentSpecification<? extends A, DS>> //
			findAgentOrder(Collection<? extends IAgentSpecification<? extends A, DS>> agentsToRun) {
		List<IAgentSpecification<? extends A, DS>> order = new ArrayList<>();

		List<InformationId> provided = new ArrayList<>();
		List<IAgentSpecification<? extends A, DS>> specsToRun = new ArrayList<>(agentsToRun);

		while (!specsToRun.isEmpty()) {
			IAgentSpecification<? extends A, DS> next = findNext(specsToRun, provided);
			if (next == null) {
				if (logger.isErrorEnabled()) {
					logger.error("Provided Information: " + provided + " | Remaining Agents: " + specsToRun);
				}
				return null;
			}
			order.add(next);
			specsToRun.remove(next);
			provided.addAll(next.getProvideIds());
		}
		return order;
	}

	private static <A extends IAgent<DS>, DS extends IDataStructure<DS>> IAgentSpecification<? extends A, DS> findNext(List<IAgentSpecification<? extends A, DS>> specsToRun,
			List<InformationId> alreadyRun) {
		for (IAgentSpecification<? extends A, DS> a : specsToRun) {
			if (alreadyRun.containsAll(a.getRequiresIds())) {
				return a;
			}
		}
		return null;
	}
}
