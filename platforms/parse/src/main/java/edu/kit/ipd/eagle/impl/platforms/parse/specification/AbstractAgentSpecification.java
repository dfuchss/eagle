package edu.kit.ipd.eagle.impl.platforms.parse.specification;

import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEAgent;
import edu.kit.ipd.eagle.impl.platforms.parse.PARSEGraphWrapper;
import edu.kit.ipd.eagle.impl.platforms.parse.prepipeline.PrePipelineMode;
import edu.kit.ipd.eagle.port.IAgentSpecification;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

/**
 * Defines a simple base class for {@link IAgentSpecification IAgentSpecifications}
 *
 * @author Dominik Fuchss
 *
 * @param <A> the actual {@link AbstractAgent} of this specification
 */
public abstract class AbstractAgentSpecification<A extends AbstractAgent> implements IAgentSpecification<PARSEAgent, PARSEGraphWrapper> {
    /**
     * The Logger for all instances of {@link AbstractAgentSpecification}.
     */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Supplier<A> agentInstance;

    /**
     * Create specification by agent.
     *
     * @param agent the agent instance
     */
    protected AbstractAgentSpecification(Supplier<A> agent) {
        this.agentInstance = agent;
    }

    @Override
    public final PARSEAgent createAgentInstance() {
        return new PARSEAgent(this.agentInstance.get());
    }

    /**
     * Get the necessary type of PrePipeline.
     *
     * @return the type of prepipeline needed
     */
    public abstract PrePipelineMode getMode();

    @Override
    public String toString() {
        return "Agent[" + this.agentInstance.get().getClass().getSimpleName() + "], Requires: " + this.getRequiresIds() + ", Provides: " + this.getProvideIds();
    }

}
