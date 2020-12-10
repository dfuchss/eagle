package edu.kit.ipd.are.agentanalysis.port;

import java.util.List;

/**
 * This interface defines the needed methods to specify an {@link IAgent}. Also
 * the dependencies between the agents are accessible.
 *
 * @param <A>  the actual agent
 * @param <DS> the data structure
 * @author Dominik Fuchss
 *
 */
public interface IAgentSpecification<A extends IAgent<DS>, DS extends IDataStructure<DS>> {
	/**
	 * Get the Agent Instance of this specification.
	 *
	 * @return the agent instance
	 */
	A getAgentInstance();

	/**
	 * Get the type of provided information of the agent.
	 *
	 * @return the list of provided information types
	 */
	List<InformationId> getProvideIds();

	/**
	 * Get the type of required information of the agent. Iff empty the agent only
	 * needs the information from the PrePipeline of PARSE.
	 *
	 * @return the list of required information types
	 */
	List<InformationId> getRequiresIds();
}
