package edu.kit.ipd.are.agentanalysis.impl.parse;

import java.util.Objects;

import edu.kit.ipd.are.agentanalysis.port.IDataStructure;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.graph.IGraph;

public class PARSEGraphWrapper implements IDataStructure<PARSEGraphWrapper> {

	private final IGraph graph;
	private final PrePipelineMode ppm;

	public PARSEGraphWrapper(IGraph graph, PrePipelineMode ppm) {
		this.graph = Objects.requireNonNull(graph);
		this.ppm = Objects.requireNonNull(ppm);
	}

	public IGraph getGraph() {
		return graph;
	}

	@Override
	public PARSEGraphWrapper clone() {
		return new PARSEGraphWrapper(graph.clone(), this.ppm);
	}

	public PrePipelineMode getPrePipelineMode() {
		return ppm;
	}
}
