package edu.kit.ipd.are.agentanalysis.impl.xplore.layer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.IAgent;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.IDataStructure;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayer;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;
import edu.kit.ipd.are.agentanalysis.port.xplore.selection.ISelectionProvider;

/**
 * Defines the realization of an {@link ILayer}.
 *
 * @author Dominik Fuchss
 * @param <A>  the type of agent to use
 * @param <DS> the type of data structure to use
 */
public final class Layer<A extends IAgent<DS>, DS extends IDataStructure<DS>> implements ILayer {

	private IAgentSpecification<? extends A, DS> agent;
	private IAgentHypothesisSpecification<? extends A, DS> agentWithHypotheses;

	private Layer<A, DS> next;

	private List<LayerEntry<A, DS>> entries;

	/**
	 * Create a new layer for a non hypothesis agent (these are normal
	 * {@link IAgentSpecification IAgentSpecifications})
	 *
	 * @param <A>   the type of agent to use
	 * @param <DS>  the type of data structure to use
	 * @param agent the agent specification
	 * @return the created layer
	 */
	public static <A extends IAgent<DS>, DS extends IDataStructure<DS>> Layer<A, DS> createLayerByNoHypAgent(IAgentSpecification<? extends A, DS> agent) {
		return new Layer<>(agent);
	}

	/**
	 * Create a new layer for a hypothesis agent (these are
	 * {@link IAgentHypothesisSpecification IAgentHypothesisSpecifications})
	 *
	 * @param <A>   the type of agent to use
	 * @param <DS>  the type of data structure to use
	 * @param agent the agent specification
	 * @return the created layer
	 */
	public static <A extends IAgent<DS>, DS extends IDataStructure<DS>> Layer<A, DS> createLayerByHypAgent(IAgentHypothesisSpecification<? extends A, DS> agent) {
		return new Layer<>(agent);
	}

	private Layer(IAgentSpecification<? extends A, DS> agent) {
		this.agent = agent;
		this.agentWithHypotheses = null;
		this.entries = new ArrayList<>();
	}

	private Layer(IAgentHypothesisSpecification<? extends A, DS> agent) {
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
	public IAgentSpecification<? extends A, DS> getAgent() {
		return this.agent;
	}

	/**
	 * Set the next layer.
	 *
	 * @param next the next layer
	 */
	public void setNext(Layer<A, DS> next) {
		this.next = next;
	}

	/**
	 * Create a new layer entry in this layer. <b>This will invoke the execution of
	 * the agent of this layer.</b>
	 *
	 * @param parent the parent layer entry
	 * @param data   the associated input data
	 * @return the new layer entry
	 */
	public LayerEntry<A, DS> createEntry(LayerEntry<A, DS> parent, DS data) {
		LayerEntry<A, DS> entry = new LayerEntry<>(this, this.entries.size(), parent, data);
		ILayer.logger.info("Created LayerEntry " + entry);
		this.entries.add(entry);
		return entry;
	}

	@Override
	public List<ILayerEntry> getEntries() {
		return Collections.unmodifiableList(this.entries);
	}

	@Override
	public void explore(ISelectionProvider selectionProvider) {
		for (LayerEntry<A, DS> entry : this.entries) {
			entry.explore(selectionProvider);
		}
	}

	Layer<A, DS> getNext() {
		return this.next;
	}

	IAgentHypothesisSpecification<? extends A, DS> getAgentWithHypotheses() {
		return this.agentWithHypotheses;
	}
}
