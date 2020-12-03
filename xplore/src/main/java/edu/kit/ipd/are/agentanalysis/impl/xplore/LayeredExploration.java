package edu.kit.ipd.are.agentanalysis.impl.xplore;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.are.agentanalysis.impl.execution.AgentHelper;
import edu.kit.ipd.are.agentanalysis.impl.xplore.layer.Layer;
import edu.kit.ipd.are.agentanalysis.port.AgentAnalysisConfiguration;
import edu.kit.ipd.are.agentanalysis.port.EnhancedGraph;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.xplore.IExploration;
import edu.kit.ipd.are.agentanalysis.port.xplore.IExplorationResult;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayer;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * The base class for all explorators which use {@link ILayer Layers}
 *
 * @author Dominik Fuchss
 *
 */
public abstract class LayeredExploration implements IExploration {
	protected static final Logger logger = LoggerFactory.getLogger(LayeredExploration.class);

	private String text;
	private IGraph initialGraph;
	private PrePipelineMode mode;

	private Layer[] layers;
	private Set<IAgentSpecification<?>> agents;
	private Set<IAgentHypothesisSpecification<?>> hypothesesAgents;

	/**
	 * Create the exploration by an inital graph.
	 *
	 * @param initial the initial graph
	 */
	protected LayeredExploration(EnhancedGraph initial) {
		this.agents = new HashSet<>();
		this.hypothesesAgents = new HashSet<>();

		this.restart(initial);
	}

	@Override
	public final void restart(EnhancedGraph initial) {
		this.layers = null;
		this.agents.clear();
		this.hypothesesAgents.clear();
		this.mode = initial.getPrePipelineMode();
		this.text = initial.getText();
		this.initialGraph = initial.getGraph();
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
	protected abstract void exploreLayers(Layer[] layers);

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

	private void createLayers() {
		this.layers = new Layer[this.agents.size()];
		var orderedAgents = AgentHelper.findAgentOrder(this.agents);
		if (orderedAgents == null) {
			throw new IllegalArgumentException("No valid order of agents possible");
		}
		for (int i = 0; i < this.layers.length; i++) {
			IAgentSpecification<?> agent = orderedAgents.get(i);
			this.layers[i] = this.hypothesesAgents.contains(agent) //
					? Layer.createLayerByHypAgent((IAgentHypothesisSpecification<?>) agent)
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
	protected void loadAgent(IAgentSpecification<?> agent) {
		if (agent == null) {
			throw new IllegalArgumentException("Agent cannot be null!");
		}
		if (!AgentAnalysisConfiguration.isOverridePrePiplineRestricitions() && agent.getMode() != this.mode) {
			throw new IllegalArgumentException("Agent " + agent + " does not support Mode " + this.mode);
		}

		this.agents.add(agent);
	}

	/**
	 * Load an agent as hypotheses agent (hypotheses will be generated).
	 *
	 * @param agent the agent
	 */
	protected void loadHypothesisAgent(IAgentHypothesisSpecification<?> agent) {
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
