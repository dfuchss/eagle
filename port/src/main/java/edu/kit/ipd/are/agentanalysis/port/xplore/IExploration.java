package edu.kit.ipd.are.agentanalysis.port.xplore;

import edu.kit.ipd.are.agentanalysis.port.EnhancedGraph;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;

/**
 * Defines an explorator which analyzes {@link IAgentSpecification} and
 * {@link IAgentHypothesisSpecification} to generate the different paths of the
 * exploration.
 *
 * @author Dominik Fuchss
 *
 */
public interface IExploration {
	/**
	 * Restart the exploration with a new graph.
	 *
	 * @param initial the new initial graph
	 */
	void restart(EnhancedGraph initial);

	/**
	 * Explore the current graph.
	 *
	 * @return the exploration result
	 */
	IExplorationResult explore();
}
