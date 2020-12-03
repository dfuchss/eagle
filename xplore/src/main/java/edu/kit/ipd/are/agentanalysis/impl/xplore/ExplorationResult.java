package edu.kit.ipd.are.agentanalysis.impl.xplore;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.xplore.IExplorationResult;
import edu.kit.ipd.are.agentanalysis.port.xplore.IPath;
import edu.kit.ipd.are.agentanalysis.port.xplore.PathUtils;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;

/**
 * Defines a simple realization of an {@link IExplorationResult}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ExplorationResult implements IExplorationResult {
	private static final long serialVersionUID = -1616553559791960403L;

	private String inputText;
	private ILayerEntry startNode;

	// For deserialize
	@SuppressWarnings("unused")
	private ExplorationResult() {
	}

	/**
	 * Create the exploration result.
	 *
	 * @param inputText the input text
	 * @param startNode the first exploration step (root layer entry)
	 */
	public ExplorationResult(String inputText, ILayerEntry startNode) {
		this.inputText = inputText;
		this.startNode = startNode;
	}

	@Override
	public ILayerEntry getExplorationRoot() {
		return this.startNode;
	}

	@Override
	public String getInputText() {
		return this.inputText;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int layer = 0;
		List<ILayerEntry> layerSteps = new ArrayList<>();
		layerSteps.add(this.startNode);

		while (!layerSteps.isEmpty()) {
			result.append("Layer " + layer + ":\n");
			List<ILayerEntry> nextLayer = new ArrayList<>();
			for (var step : layerSteps) {
				result.append(step);
				result.append("\n");
				nextLayer.addAll(step.getChildren());
			}
			layerSteps.clear();
			layerSteps.addAll(nextLayer);
			layer++;
		}
		return result.toString();
	}

	@Override
	public List<IPath> getPaths() {
		return PathUtils.getPaths(this.getExplorationRoot());
	}
}
