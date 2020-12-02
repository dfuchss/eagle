package edu.kit.ipd.are.agentanalysis.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * The main interface for the execution of agents. This interface provides
 * methods to load {@link IAgentSpecification IAgentSpecs} and execute them on a
 * {@link IGraph}.
 *
 * @author Dominik Fuchss
 *
 */
public interface IAgentExecution {

	/**
	 * The Logger for all instances of {@link IAgentExecution}.
	 */
	Logger logger = LoggerFactory.getLogger(IAgentExecution.class);

	/**
	 * Load the agents you want to use in the analysis.
	 *
	 * @param agentSpecs the agent specifications
	 */
	void loadAgents(IAgentSpecification<?>... agentSpecs);

	/**
	 * Unload all agents.
	 */
	void unloadAgents();

	/**
	 * Execute agents on an input. The agents specified in
	 * {@link #loadAgents(IAgentSpecification...)} will be executed on the graph.
	 *
	 * @param graph the graph
	 * @return the resulting graph (original will not be modified)
	 */
	IGraph execute(EnhancedGraph graph);

}
