package edu.kit.ipd.are.agentanalysis.impl.xplore.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayer;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;
import edu.kit.ipd.are.agentanalysis.port.xplore.selection.ISelectionProvider;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * Defines the realization of an {@link ILayer}.
 *
 * @author Dominik Fuchss
 *
 */
public final class Layer implements ILayer {

	private IAgentSpecification<?> agent;
	private IAgentHypothesisSpecification<?> agentWithHypotheses;

	private Layer next;

	private List<LayerEntry> entries;

	/**
	 * Create a new layer for a non hypothesis agent (these are normal
	 * {@link IAgentSpecification IAgentSpecifications})
	 *
	 * @param agent the agent specification
	 * @return the created layer
	 */
	public static Layer createLayerByNoHypAgent(IAgentSpecification<?> agent) {
		return new Layer(agent);
	}

	/**
	 * Create a new layer for a hypothesis agent (these are
	 * {@link IAgentHypothesisSpecification IAgentHypothesisSpecifications})
	 *
	 * @param agent the agent specification
	 * @return the created layer
	 */
	public static Layer createLayerByHypAgent(IAgentHypothesisSpecification<?> agent) {
		return new Layer(agent);
	}

	private Layer(IAgentSpecification<?> agent) {
		this.agent = agent;
		this.agentWithHypotheses = null;
		this.entries = new ArrayList<>();
	}

	private Layer(IAgentHypothesisSpecification<?> agent) {
		this.agent = agent;
		this.agentWithHypotheses = agent;
		this.entries = new ArrayList<>();
	}

	/**
	 * Get the hypotheses range of this layer.
	 *
	 * @return the range of the associated {@link IAgentHypothesisSpecification} or
	 *         {@code null} iff just a no hypotheses agent is associated.
	 */
	public HypothesisRange getHypothesesRange() {
		return this.agentWithHypotheses == null ? null : this.agentWithHypotheses.getHypothesesRange();
	}

	/**
	 * Get the agent which belong to this layer.
	 *
	 * @return the agent
	 */
	public IAgentSpecification<?> getAgent() {
		return this.agent;
	}

	/**
	 * Set the next layer.
	 *
	 * @param next the next layer
	 */
	public void setNext(Layer next) {
		this.next = next;
	}

	/**
	 * Create a new layer entry in this layer. <b>This will invoke the execution of
	 * the agent of this layer.</b>
	 *
	 * @param parent the parent layer entry
	 * @param graph  the associated input graph
	 * @return the new layer entry
	 */
	public ILayerEntry createEntry(LayerEntry parent, IGraph graph) {
		LayerEntry entry = new LayerEntry(this, this.entries.size(), parent, graph);
		logger.info("Created LayerEntry " + entry);
		this.entries.add(entry);
		return entry;
	}

	@Override
	public List<ILayerEntry> getEntries() {
		return Collections.unmodifiableList(this.entries);
	}

	@Override
	public void explore(ISelectionProvider selectionProvider) {
		for (LayerEntry entry : this.entries) {
			entry.explore(selectionProvider);
		}
	}

	Layer getNext() {
		return this.next;
	}

	IAgentHypothesisSpecification<?> getAgentWithHypotheses() {
		return this.agentWithHypotheses;
	}
}
