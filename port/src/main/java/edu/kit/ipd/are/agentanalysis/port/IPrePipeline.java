package edu.kit.ipd.are.agentanalysis.port;

/**
 * Defines a pre pipeline which generates a data structure from an input text.
 *
 * @author Dominik Fuchss
 * @param <DS> data structure
 */
public interface IPrePipeline<DS extends IDataStructure<DS>> {
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
	DS createGraph(String text);
}
