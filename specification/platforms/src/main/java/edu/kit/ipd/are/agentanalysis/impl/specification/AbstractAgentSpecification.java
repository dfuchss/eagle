package edu.kit.ipd.are.agentanalysis.impl.specification;

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
public abstract class AbstractAgentSpecification<A extends AbstractAgent> implements IAgentSpecification<A> {
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
	public final A getAgentInstance() {
		return this.agentInstance;
	}

	/**
	 * Set the agent instance.
	 *
	 * @param agent the agent instance
	 */
	public void setAgentInstance(A agent) {
		this.agentInstance = agent;
	}

	@Override
	public String toString() {
		return "Agent[" + this.agentInstance.getClass().getSimpleName() + "], Requires: " + this.getRequiresIds() + ", Provides: " + this.getProvideIds();
	}

}
