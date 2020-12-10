package edu.kit.ipd.are.agentanalysis.impl.xplore;

import java.util.HashMap;
import java.util.Map;

import edu.kit.ipd.are.agentanalysis.impl.xplore.layer.Layer;
import edu.kit.ipd.are.agentanalysis.port.IAgent;
import edu.kit.ipd.are.agentanalysis.port.IDataStructure;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.xplore.IInitialData;
import edu.kit.ipd.are.agentanalysis.port.xplore.selection.ISelectionProvider;

/**
 * A layered exploration with the possibility to override the default selection
 * provider of an {@link IAgentHypothesisSpecification}.
 *
 * @author Dominik Fuchss
 *
 */
public class SpecificExploration<A extends IAgent<DS>, DS extends IDataStructure<DS>> extends SimpleExploration<A, DS> {

	private Map<IAgentHypothesisSpecification<? extends A, DS>, ISelectionProvider> selectors;

	/**
	 * Create a new exploration by an initial graph and a maximum for the generated
	 * hypotheses of the {@link IAgentHypothesisSpecification
	 * IAgentHypothesisSpecifications}.
	 *
	 * @param initial the initial graph
	 * @param maxHyps the maximum amount of hypotheses for the
	 *                {@link IAgentHypothesisSpecification
	 *                IAgentHypothesisSpecifications}
	 */
	public SpecificExploration(IInitialData<DS> initial, int maxHyps) {
		super(initial, maxHyps);
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
	 * Same as {@link #loadHypothesisAgent(IAgentHypothesisSpecification)} but
	 * override the default selector.
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
