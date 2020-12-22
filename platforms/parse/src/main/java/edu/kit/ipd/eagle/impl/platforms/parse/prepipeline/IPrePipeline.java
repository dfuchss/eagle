package edu.kit.ipd.eagle.impl.platforms.parse.prepipeline;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEGraphWrapper;

/**
 * Defines a pre pipeline which generates a data structure from an input text.
 *
 * @author Dominik Fuchss
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
	PARSEGraphWrapper createGraph(String text);
}
