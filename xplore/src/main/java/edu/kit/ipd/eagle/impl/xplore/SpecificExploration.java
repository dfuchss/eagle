package edu.kit.ipd.eagle.impl.xplore;

import java.util.HashMap;
import java.util.Map;

import edu.kit.ipd.eagle.impl.xplore.layer.Layer;
import edu.kit.ipd.eagle.port.IAgent;
import edu.kit.ipd.eagle.port.IDataStructure;
import edu.kit.ipd.eagle.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.eagle.port.xplore.IExplorationResult;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * A layered exploration with the possibility to override the default selection provider of an
 * {@link IAgentHypothesisSpecification}.
 *
 * @author Dominik Fuchss
 * @param <A>  the type of agent for exploration
 * @param <DS> the type of data structure to use
 */
public class SpecificExploration<A extends IAgent<DS>, DS extends IDataStructure<DS>> extends SimpleExploration<A, DS> {

    private Map<IAgentHypothesisSpecification<? extends A, DS>, ISelectionProvider> selectors;

    /**
     * Create a new exploration by an initial data structure and a maximum for the generated hypotheses of the
     * {@link IAgentHypothesisSpecification IAgentHypothesisSpecifications}.
     *
     *
     * @param initial the initial data
     * @param id      the value for {@link IExplorationResult#getId()}. May be an identifier for the current
     *                exploration.
     * @param maxHyps the maximum amount of hypotheses for the {@link IAgentHypothesisSpecification
     *                IAgentHypothesisSpecifications}
     */
    public SpecificExploration(DS initial, String id, int maxHyps) {
        this(initial, id, maxHyps, false, false);
    }

    /**
     * Create a new exploration by an initial data structure and a maximum for the generated hypotheses of the
     * {@link IAgentHypothesisSpecification IAgentHypothesisSpecifications}.
     *
     *
     * @param initial                   the initial data
     * @param id                        the value for {@link IExplorationResult#getId()}. May be an identifier for the
     *                                  current exploration.
     * @param maxHyps                   the maximum amount of hypotheses for the {@link IAgentHypothesisSpecification
     *                                  IAgentHypothesisSpecifications}
     *
     * @param initializeDummyAgentAtEnd indicates whether a dummy agent shall be initialised at the end to invoke the
     *                                  selection process for the last agent
     * @param parallel                  indicator for parallel exploration
     */
    public SpecificExploration(DS initial, String id, int maxHyps, boolean initializeDummyAgentAtEnd, boolean parallel) {
        super(initial, id, maxHyps, initializeDummyAgentAtEnd, parallel);
        this.selectors = new HashMap<>();
    }

    @Override
    protected ISelectionProvider getSelectionProvider(Layer<A, DS> layer) {
        if (this.selectors.containsKey(layer.getAgent())) {
            return this.selectors.get(layer.getAgent());
        }
        return super.getSelectionProvider(layer);
    }

    /**
     * Same as {@link #loadHypothesisAgent(IAgentHypothesisSpecification)} but override the default selector.
     *
     * @param agent    the agent
     * @param selector the selection provider
     */
    public void loadHypothesisAgent(IAgentHypothesisSpecification<? extends A, DS> agent, ISelectionProvider selector) {
        super.loadHypothesisAgent(agent);
        this.selectors.put(agent, selector);
    }

    @Override
    public void clearAgents() {
        super.clearAgents();
        this.selectors.clear();
    }
}
