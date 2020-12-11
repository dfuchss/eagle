package edu.kit.ipd.are.agentanalysis.port;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main interface for the execution of agents. This interface provides
 * methods to load {@link IAgentSpecification IAgentSpecs} and execute them on a
 * data structure
 *
 * @author Dominik Fuchss
 * @param <A>  the actual agent type
 * @param <DS> Data Structure for input and output
 * @param <AS> the agent specification type
 */
public interface IAgentExecution<A extends IAgent<DS>, DS extends IDataStructure<DS>, AS extends IAgentSpecification<A, DS>> {

	/**
	 * The Logger for all instances of {@link IAgentExecution}.
	 */
	Logger logger = LoggerFactory.getLogger(IAgentExecution.class);

	/**
	 * Load the agent you want to use in the analysis.
	 *
	 * @param agentSpec the agent specification
	 */
	void loadAgent(AS agentSpec);

	/**
	 * Load agents you want to use in the analysis.
	 *
	 * @param agentSpecs the agent specifications
	 */
	default void loadAgents(Collection<AS> agentSpecs) {
		for (var spec : agentSpecs) {
			this.loadAgent(spec);
		}
	}

	/**
	 * Unload all agents.
	 */
	void unloadAgents();

	/**
	 * Execute agents on an input. The agents specified in
	 * {@link #loadAgents(IAgentSpecification...)} will be executed on the graph.
	 *
	 * @param input the input data structure
	 * @return the output data structure (original will not be modified)
	 */
	DS execute(DS input);

}
