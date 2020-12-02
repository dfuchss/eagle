package edu.kit.ipd.are.agentanalysis.port.hypothesis;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * Defines a manager for {@link IHypothesis hypothesis}. Normally that would be
 * the {@link AbstractAgent} which generates.
 *
 * @author Dominik Fuchss
 *
 */
public interface IHypothesesManager {

	/**
	 * Retrieve the "normal" results of an {@link IAgentSpecification} as pseudo
	 * hypotheses.
	 *
	 * @param graph the graph
	 * @return a list of found pseudo hypotheses groups
	 */
	List<IHypothesesSet> getHypothesesForNonHypothesesExecution(IGraph graph);

	/**
	 * Retrieve the hypotheses stored in a {@link IGraph ParseGraph}
	 *
	 * @param graph the graph
	 * @return a list of found hypotheses groups
	 */
	List<IHypothesesSet> getHypothesesFromGraph(IGraph graph);

	/**
	 * Apply a selection of hypotheses to a graph.
	 *
	 * @param graph      the graph
	 * @param hypotheses the selected hypotheses
	 */
	void applyHypothesesToGraph(IGraph graph, List<IHypothesesSelection> hypotheses);

}
