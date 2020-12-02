package edu.kit.ipd.are.agentanalysis.port;

import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * Defines a pre pipeline which generates an {@link IGraph} from an input text.
 *
 * @author Dominik Fuchss
 *
 */
public interface IPrePipeline {
	/**
	 * Get the {@link PrePipelineMode} of this pipeline.
	 *
	 * @return the mode
	 */
	PrePipelineMode getMode();

	/**
	 * Create the PARSE graph by text.
	 *
	 * @param text the text
	 * @return the graph or {@code null} if prepipeline failed
	 */
	IGraph createGraph(String text);
}
