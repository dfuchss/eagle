package edu.kit.ipd.are.agentanalysis.impl.xplore;

import java.util.HashMap;
import java.util.Map;

import edu.kit.ipd.are.agentanalysis.impl.xplore.layer.Layer;
import edu.kit.ipd.are.agentanalysis.port.EnhancedGraph;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.xplore.selection.ISelectionProvider;

/**
 * A layered exploration with the possibility to override the default selection
 * provider of an {@link IAgentHypothesisSpecification}.
 *
 * @author Dominik Fuchss
 *
 */
public class SpecificExploration extends SimpleExploration {

	private Map<IAgentHypothesisSpecification<?>, ISelectionProvider> selectors;

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
	public SpecificExploration(EnhancedGraph initial, int maxHyps) {
		super(initial, maxHyps);
		this.selectors = new HashMap<>();
	}

	@Override
	protected ISelectionProvider getSelectionProvider(Layer layer) {
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
	public void loadHypothesisAgent(IAgentHypothesisSpecification<?> agent, ISelectionProvider selector) {
		super.loadHypothesisAgent(agent);
		this.selectors.put(agent, selector);
	}

	@Override
	public void clearAgents() {
		super.clearAgents();
		this.selectors.clear();
	}
}
