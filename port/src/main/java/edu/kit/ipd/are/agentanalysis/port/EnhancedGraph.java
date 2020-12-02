package edu.kit.ipd.are.agentanalysis.port;

import java.util.Objects;

import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * This class provides an {@link IGraph} based on a text input and an
 * {@link IPrePipeline}.
 *
 * @author Dominik Fuchss
 *
 */
public final class EnhancedGraph {
	private final IGraph graph;
	private final IPrePipeline prepipeline;
	private final String text;

	/**
	 * Create a new EnhancedGraph by text and prepipeline.
	 *
	 * @param text        the text
	 * @param prepipeline the prepipeline
	 */
	public EnhancedGraph(String text, IPrePipeline prepipeline) {
		this.text = text;
		this.graph = Objects.requireNonNull(prepipeline.createGraph(text), "PrePipeline failed");
		this.prepipeline = prepipeline;
	}

	/**
	 * Get the input text.
	 *
	 * @return the input text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Get the {@link IGraph}
	 *
	 * @return the graph
	 */
	public IGraph getGraph() {
		return this.graph;
	}

	/**
	 * Get the selected prepipeline mode.
	 *
	 * @return the prepipeline mode.
	 */
	public PrePipelineMode getPrePipelineMode() {
		return this.prepipeline.getMode();
	}
}
