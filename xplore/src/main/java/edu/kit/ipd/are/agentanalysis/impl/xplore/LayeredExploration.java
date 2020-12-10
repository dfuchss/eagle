package edu.kit.ipd.are.agentanalysis.impl.xplore;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.are.agentanalysis.impl.xplore.layer.Layer;
import edu.kit.ipd.are.agentanalysis.port.AgentHelper;
import edu.kit.ipd.are.agentanalysis.port.IAgent;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.IDataStructure;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.xplore.IExploration;
import edu.kit.ipd.are.agentanalysis.port.xplore.IExplorationResult;
import edu.kit.ipd.are.agentanalysis.port.xplore.IInitialData;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayer;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;

/**
 * The base class for all explorators which use {@link ILayer Layers}
 *
 * @author Dominik Fuchss
 *
 */
public abstract class LayeredExploration<A extends IAgent<DS>, DS extends IDataStructure<DS>> implements IExploration<DS> {
	protected static final Logger logger = LoggerFactory.getLogger(LayeredExploration.class);

	private String text;
	private DS initialGraph;

	private Layer<A, DS>[] layers;
	private Set<IAgentSpecification<? extends A, DS>> agents;
	private Set<IAgentHypothesisSpecification<? extends A, DS>> hypothesesAgents;

	/**
	 * Create the exploration by an inital graph.
	 *
	 * @param initial the initial graph
	 */
	protected LayeredExploration(IInitialData<DS> initial) {
		this.agents = new HashSet<>();
		this.hypothesesAgents = new HashSet<>();

		this.restart(initial);
	}

	@Override
	public final void restart(IInitialData<DS> initial) {
		this.layers = null;
		this.agents.clear();
		this.hypothesesAgents.clear();
		this.text = initial.getText();
		this.initialGraph = initial.getData();
	}

	@Override
	public final IExplorationResult explore() {
		this.createLayers();
		this.createRoot();
		this.exploreLayers(this.layers);
		return this.createExplorationResult();
	}

	/**
	 * Explore the defined layers.
	 *
	 * @param layers the layers
	 */
	protected abstract void exploreLayers(Layer<A, DS>[] layers);

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
		this.layers[0].createEntry(null, this.initialGraph);
	}

	@SuppressWarnings("unchecked")
	private void createLayers() {
		this.layers = (Layer<A, DS>[]) Array.newInstance(Layer.class, this.agents.size());
		var orderedAgents = AgentHelper.findAgentOrder(this.agents);
		if (orderedAgents == null) {
			throw new IllegalArgumentException("No valid order of agents possible");
		}
		for (int i = 0; i < this.layers.length; i++) {
			IAgentSpecification<? extends A, DS> agent = orderedAgents.get(i);
			this.layers[i] = this.hypothesesAgents.contains(agent) //
					? Layer.createLayerByHypAgent((IAgentHypothesisSpecification<? extends A, DS>) agent)
					: Layer.createLayerByNoHypAgent(agent);
			if (i != 0) {
				this.layers[i - 1].setNext(this.layers[i]);
			}
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

}
