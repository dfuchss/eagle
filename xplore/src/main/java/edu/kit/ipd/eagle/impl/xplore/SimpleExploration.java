package edu.kit.ipd.eagle.impl.xplore;

import edu.kit.ipd.eagle.impl.xplore.layer.Layer;
import edu.kit.ipd.eagle.impl.xplore.selection.SameWordSameDecision;
import edu.kit.ipd.eagle.impl.xplore.selection.TopXConfidence;
import edu.kit.ipd.eagle.impl.xplore.selection.TopXSlidingWindow;
import edu.kit.ipd.eagle.port.IAgent;
import edu.kit.ipd.eagle.port.IAgentSpecification;
import edu.kit.ipd.eagle.port.IDataStructure;
import edu.kit.ipd.eagle.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.eagle.port.xplore.IExplorationResult;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * Defines a simple layered exploration.
 *
 * @author Dominik Fuchss
 * @param <A>  the type of agent for exploration
 * @param <DS> the type of data structure to use
 */
public class SimpleExploration<A extends IAgent<DS>, DS extends IDataStructure<DS>> extends LayeredExploration<A, DS> {

    private int maxHyps;

    /**
     * Create a new exploration by an initial data structure and a maximum for the generated hypotheses of the
     * {@link IAgentHypothesisSpecification IAgentHypothesisSpecifications}.
     *
     * @param initial the initial data
     * @param id      the value for {@link IExplorationResult#getId()}. May be an identifier for the current
     *                exploration.
     * @param maxHyps the maximum amount of hypotheses for the {@link IAgentHypothesisSpecification
     *                IAgentHypothesisSpecifications}
     */
    public SimpleExploration(DS initial, String id, int maxHyps) {
        this(initial, id, maxHyps, false, false);
    }

    /**
     * Create a new exploration by an initial data structure and a maximum for the generated hypotheses of the
     * {@link IAgentHypothesisSpecification IAgentHypothesisSpecifications}.
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
    public SimpleExploration(DS initial, String id, int maxHyps, boolean initializeDummyAgentAtEnd, boolean parallel) {
        super(initial, id, initializeDummyAgentAtEnd, parallel);
        this.maxHyps = maxHyps;
    }

    @Override
    protected void exploreLayers(Layer<A, DS>[] layers, boolean parallel) {
        for (int l = 0; l < layers.length - 1; l++) {
            Layer<A, DS> layer = layers[l];
            ISelectionProvider isp = this.getSelectionProvider(layer);
            layer.explore(isp, parallel);
        }
    }

    /**
     * Get the selection provider associated with a layer.
     *
     * @param layer the layer
     * @return the selection provider to be used for this layer
     */
    protected ISelectionProvider getSelectionProvider(Layer<A, DS> layer) throws Error {
        var range = layer.getHypothesesRange();
        if (range == null) {
            return null;
        }
        return switch (range) {
        case INPUT -> new TopXSlidingWindow(this.maxHyps, this.maxHyps);
        case ELEMENT -> new SameWordSameDecision(new TopXConfidence(this.maxHyps));
        default -> throw new UnsupportedOperationException("Unknown Range .. please add case for " + range);
        };
    }

    @Override
    public void loadAgent(IAgentSpecification<? extends A, DS> agent) {
        super.loadAgent(agent);
    }

    @Override
    public void loadHypothesisAgent(IAgentHypothesisSpecification<? extends A, DS> agent) {
        super.loadHypothesisAgent(agent);
    }

}
