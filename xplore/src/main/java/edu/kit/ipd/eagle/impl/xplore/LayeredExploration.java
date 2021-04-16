package edu.kit.ipd.eagle.impl.xplore;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.eagle.impl.xplore.layer.Layer;
import edu.kit.ipd.eagle.port.AgentHelper;
import edu.kit.ipd.eagle.port.IAgent;
import edu.kit.ipd.eagle.port.IAgentSpecification;
import edu.kit.ipd.eagle.port.IDataStructure;
import edu.kit.ipd.eagle.port.IInformationId;
import edu.kit.ipd.eagle.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.eagle.port.xplore.IExploration;
import edu.kit.ipd.eagle.port.xplore.IExplorationResult;
import edu.kit.ipd.eagle.port.xplore.layer.ILayer;
import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;

/**
 * The base class for all explorators which use {@link ILayer Layers}
 *
 * @author Dominik Fuchss
 * @param <A>  the type of agent for exploration
 * @param <DS> the type of data structure to use
 *
 */
public abstract class LayeredExploration<A extends IAgent<DS>, DS extends IDataStructure<DS>> implements IExploration<DS> {
    protected static final Logger logger = LoggerFactory.getLogger(LayeredExploration.class);

    private DS initialData;

    private Layer<A, DS>[] layers;
    private Set<IAgentSpecification<? extends A, DS>> agents;
    private Set<IAgentHypothesisSpecification<? extends A, DS>> hypothesesAgents;

    private String text;

    private final boolean initializeDummyAgentAtEnd;

    private final boolean parallel;

    /**
     * Create the exploration by an initial data structure.
     *
     * @param initial                   the initial data structure
     * @param id                        the value for {@link IExplorationResult#getId()}. May be an identifier for the
     *                                  current exploration.
     * @param initializeDummyAgentAtEnd indicates whether a dummy agent shall be initialised at the end to invoke the
     *                                  selection process for the last agent
     * @param parallel                  indicator for parallel exploration
     */
    protected LayeredExploration(DS initial, String id, boolean initializeDummyAgentAtEnd, boolean parallel) {
        this.agents = new HashSet<>();
        this.hypothesesAgents = new HashSet<>();
        this.initializeDummyAgentAtEnd = initializeDummyAgentAtEnd;
        this.parallel = parallel;
        this.restart(initial, id);
    }

    @Override
    public final void restart(DS initial, String text) {
        this.layers = null;
        this.agents.clear();
        this.hypothesesAgents.clear();
        this.initialData = initial;
        this.text = text;
    }

    @Override
    public final IExplorationResult explore() {
        this.createLayers();
        this.createRoot();
        this.exploreLayers(this.layers, this.parallel);
        return this.createExplorationResult();
    }

    /**
     * Explore the defined layers.
     *
     * @param layers   the layers
     * @param parallel indicator for parallel execution
     */
    protected abstract void exploreLayers(Layer<A, DS>[] layers, boolean parallel);

    /**
     * Get the root entry.
     *
     * @return the root layer entry or {@code null} iff none exist
     */
    protected ILayerEntry getRoot() {
        if (this.layers == null || this.layers.length == 0) {
            return null;
        }
        if (this.layers[0].getEntries().isEmpty()) {
            return null;
        }
        return this.layers[0].getEntries().get(0);
    }

    private void createRoot() {
        this.layers[0].addEmptyEntry(null, this.initialData).create();
    }

    @SuppressWarnings("unchecked")
    private void createLayers() {
        if (this.agents.isEmpty()) {
            throw new IllegalStateException("At least one agent has to be configured!");
        }

        int size = this.agents.size() + (initializeDummyAgentAtEnd ? 1 : 0);

        this.layers = (Layer<A, DS>[]) Array.newInstance(Layer.class, size);
        var orderedAgents = AgentHelper.findAgentOrder(this.agents);
        if (orderedAgents == null) {
            throw new IllegalArgumentException("No valid order of agents possible");
        }
        for (int i = 0; i < this.agents.size(); i++) {
            IAgentSpecification<? extends A, DS> agent = orderedAgents.get(i);
            this.layers[i] = this.hypothesesAgents.contains(agent) //
                    ? Layer.createLayerByHypAgent((IAgentHypothesisSpecification<? extends A, DS>) agent)
                    : Layer.createLayerByNoHypAgent(agent);
            if (i != 0) {
                this.layers[i - 1].setNext(this.layers[i]);
            }
        }

        if (initializeDummyAgentAtEnd) {
            this.layers[this.layers.length - 1] = Layer.createLayerByNoHypAgent(new DummySpec());
            this.layers[this.layers.length - 2].setNext(this.layers[this.layers.length - 1]);
        }
    }

    /**
     * Load an agent as no hypotheses agent (no hypotheses will be generated).
     *
     * @param agent the agent
     */
    protected void loadAgent(IAgentSpecification<? extends A, DS> agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null!");
        }

        this.agents.add(agent);
    }

    /**
     * Load an agent as hypotheses agent (hypotheses will be generated).
     *
     * @param agent the agent
     */
    protected void loadHypothesisAgent(IAgentHypothesisSpecification<? extends A, DS> agent) {
        this.loadAgent(agent);
        this.hypothesesAgents.add(agent);
    }

    /**
     * Unload all agents.
     */
    public void clearAgents() {
        this.agents.clear();
        this.hypothesesAgents.clear();
        this.layers = null;
    }

    private ExplorationResult createExplorationResult() {
        ILayerEntry startNode = this.layers[0].getEntries().get(0);
        return new ExplorationResult(this.text, startNode);
    }

    private class DummySpec implements IAgentSpecification<A, DS> {

        private DummyAgent dummyAgent = new DummyAgent();

        // This cast has no effect in Java .. but handle with caution ..
        @SuppressWarnings("unchecked")
        @Override
        public A createAgentInstance() {
            return (A) dummyAgent;
        }

        @Override
        public List<? extends IInformationId> getProvideIds() {
            return List.of();
        }

        @Override
        public List<? extends IInformationId> getRequiresIds() {
            return List.of();
        }

    }

    private class DummyAgent implements IAgent<DS> {

        @Override
        public DS execute(DS input) {
            return input.createCopy();
        }

        @Override
        public String getName() {
            return "Dummy Agent";
        }

    }
}
