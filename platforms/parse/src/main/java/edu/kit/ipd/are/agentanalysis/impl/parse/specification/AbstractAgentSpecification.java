package edu.kit.ipd.are.agentanalysis.impl.parse.specification;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgent;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.prepipeline.PrePipelineMode;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

/**
 * Defines a simple base class for {@link IAgentSpecification
 * IAgentSpecifications}
 *
 * @author Dominik Fuchss
 *
 * @param <A> the actual {@link AbstractAgent} of this specification
 */
public abstract class AbstractAgentSpecification<A extends AbstractAgent> implements IAgentSpecification<PARSEAgent, PARSEGraphWrapper> {
	protected A agentInstance;

	/**
	 * Create specification by agent.
	 *
	 * @param agent the agent instance
	 */
	protected AbstractAgentSpecification(A agent) {
		this.agentInstance = agent;
	}

	@Override
	public final PARSEAgent getAgentInstance() {
		return new PARSEAgent(this.agentInstance);
	}

	/**
	 * Get the necessary type of PrePipeline.
	 *
	 * @return the type of prepipeline needed
	 */
	public abstract PrePipelineMode getMode();

	@Override
	public String toString() {
		return "Agent[" + this.agentInstance.getClass().getSimpleName() + "], Requires: " + this.getRequiresIds() + ", Provides: " + this.getProvideIds();
	}

}
