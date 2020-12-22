package edu.kit.ipd.eagle.impl.parse.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.eagle.impl.parse.PARSEAgent;
import edu.kit.ipd.eagle.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.eagle.impl.parse.prepipeline.PrePipelineMode;
import edu.kit.ipd.eagle.port.IAgentSpecification;
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
	/**
	 * The Logger for all instances of {@link AbstractAgentSpecification}.
	 */
	protected final Logger logger = LoggerFactory.getLogger(AbstractAgentSpecification.class);

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
