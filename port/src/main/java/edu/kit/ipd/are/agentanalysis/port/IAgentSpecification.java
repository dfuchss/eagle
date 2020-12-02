package edu.kit.ipd.are.agentanalysis.port;

import java.util.List;

import edu.kit.ipd.parse.luna.agent.AbstractAgent;

/**
 * This interface defines the needed methods to specify an
 * {@link AbstractAgent}. Also the dependencies between the agents are
 * accessible.
 *
 * @param <A> the actual {@link AbstractAgent}
 * @author Dominik Fuchss
 *
 */
public interface IAgentSpecification<A extends AbstractAgent> {
	/**
	 * Get the Agent Instance of this specification.
	 *
	 * @return the agent instance
	 */
	A getAgentInstance();

	/**
	 * Get the necessary type of PrePipeline.
	 *
	 * @return the type of prepipeline needed
	 */
	PrePipelineMode getMode();

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
