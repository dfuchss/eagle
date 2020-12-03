package edu.kit.ipd.are.agentanalysis.impl.xplore;

import edu.kit.ipd.are.agentanalysis.impl.xplore.layer.Layer;
import edu.kit.ipd.are.agentanalysis.impl.xplore.selection.SameWordSameDecision;
import edu.kit.ipd.are.agentanalysis.impl.xplore.selection.SimpleUseConfidence;
import edu.kit.ipd.are.agentanalysis.impl.xplore.selection.TopXConfidence;
import edu.kit.ipd.are.agentanalysis.port.EnhancedGraph;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.xplore.selection.ISelectionProvider;

/**
 * Defines a simple layered exploration.
 *
 * @author Dominik Fuchss
 *
 */
public class SimpleExploration extends LayeredExploration {

	private int maxHyps;

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
	public SimpleExploration(EnhancedGraph initial, int maxHyps) {
		super(initial);
		this.maxHyps = maxHyps;
	}

	@Override
	protected void exploreLayers(Layer[] layers) {
		for (int l = 0; l < layers.length - 1; l++) {
			Layer layer = layers[l];
			ISelectionProvider isp = this.getSelectionProvider(layer);
			layer.explore(isp);
		}
	}

	/**
	 * Get the selection provider associated with a layer.
	 *
	 * @param layer the layer
	 * @return the selection provider to be used for this layer
	 */
	protected ISelectionProvider getSelectionProvider(Layer layer) throws Error {
		var range = layer.getHypothesesRange();
		if (range == null) {
			return null;
		}
		return switch (range) {
		case SENTENCE -> new SimpleUseConfidence(this.maxHyps, this.maxHyps);
		case NODE -> new SameWordSameDecision(new TopXConfidence(this.maxHyps));
		default -> throw new UnsupportedOperationException("Unknown Range .. please add case for " + range);
		};
	}

	@Override
	public void loadAgent(IAgentSpecification<?> agent) {
		super.loadAgent(agent);
	}

	@Override
	public void loadHypothesisAgent(IAgentHypothesisSpecification<?> agent) {
		super.loadHypothesisAgent(agent);
	}

}
