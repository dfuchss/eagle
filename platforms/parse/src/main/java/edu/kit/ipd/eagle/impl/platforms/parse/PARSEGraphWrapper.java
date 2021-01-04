package edu.kit.ipd.eagle.impl.platforms.parse;

import java.util.Objects;

import edu.kit.ipd.eagle.impl.platforms.parse.prepipeline.PrePipelineMode;
import edu.kit.ipd.eagle.port.IDataStructure;
import edu.kit.ipd.parse.luna.graph.IGraph;

/**
 * Adapter that adapts an {@link IGraph} to an {@link IDataStructure}.
 *
 * @author Dominik Fuchss
 *
 */
public class PARSEGraphWrapper implements IDataStructure<PARSEGraphWrapper> {
	private final String text;
	private final IGraph graph;
	private final PrePipelineMode ppm;

	/**
	 * Create a new PARSEGraphWrapper based on the {@link IGraph}, the text, and the
	 * {@link PrePipelineMode}.
	 *
	 * @param graph the graph
	 * @param text  the text
	 * @param ppm   the {@link PrePipelineMode}
	 */
	public PARSEGraphWrapper(IGraph graph, String text, PrePipelineMode ppm) {
		this.text = Objects.requireNonNull(text);
		this.graph = Objects.requireNonNull(graph);
		this.ppm = Objects.requireNonNull(ppm);
	}

	/**
	 * Get the Graph.
	 *
	 * @return the graph
	 */
	public IGraph getGraph() {
		return this.graph;
	}

	/**
	 * Create a new {@link PARSEGraphWrapper} based on a new graph.
	 *
	 * @param graph the graph
	 * @return the new {@link PARSEGraphWrapper}
	 */
	public PARSEGraphWrapper newGraph(IGraph graph) {
		return new PARSEGraphWrapper(graph, this.text, this.ppm);
	}

	@Override
	public PARSEGraphWrapper createCopy() {
		return new PARSEGraphWrapper(this.graph.clone(), this.text, this.ppm);
	}

	/**
	 * Get the {@link PrePipelineMode}.
	 *
	 * @return the {@link PrePipelineMode}
	 */
	public PrePipelineMode getPrePipelineMode() {
		return this.ppm;
	}

	@Override
	public String getText() {
		return this.text;
	}
}
