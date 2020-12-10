package edu.kit.ipd.are.agentanalysis.impl.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import edu.kit.ipd.parse.luna.graph.IArc;
import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.INode;

/**
 * Some utilites to work with {@link IGraph IGraphs}.
 *
 * @author Dominik Fuchss
 *
 */
public final class GraphUtils {
	private GraphUtils() {
		throw new IllegalAccessError();
	}

	/**
	 * Extract the {@link INode} which is interlinked with the hypotheses (and given
	 * to the constructor) in a new (cloned) graph.
	 *
	 * @param newGraph  a cloned graph of the base graph
	 * @param baseGraph the base graph
	 * @param baseNode  the node to be found
	 * @return the node clone of the base node
	 */
	public static INode getNodeFromClonedGraph(IGraph newGraph, IGraph baseGraph, INode baseNode) {
		String type = baseNode.getType().getName();
		// Find Index of Node in Graph:
		List<INode> originalNodesOfType = baseGraph.getNodesOfType(baseNode.getType());
		int idx = originalNodesOfType.indexOf(baseNode);
		if (idx == -1) {
			throw new IllegalArgumentException("BaseNode not found in BaseGraph");
		}

		List<INode> newNodesOfType = newGraph.getNodesOfType(newGraph.getNodeType(type));
		if (newNodesOfType.size() != originalNodesOfType.size()) {
			throw new IllegalArgumentException("Nodes of type of BaseNode: Amount mismatch!");
		}
		return newNodesOfType.get(idx);
	}

	/**
	 * Get some stats of the parse graph as text.
	 *
	 * @param graph the graph
	 * @return some stats as count of nodes or arcs
	 */
	public static String getStats(IGraph graph) {
		if (graph == null) {
			return "No Graph";
		}

		List<String> nodeAttributeNames = new ArrayList<>();
		for (INode node : graph.getNodes()) {
			nodeAttributeNames.addAll(node.getAttributeNames());
		}
		nodeAttributeNames = new ArrayList<>(new TreeSet<>(nodeAttributeNames));

		List<String> arcAttributeNames = new ArrayList<>();
		for (IArc arc : graph.getArcs()) {
			arcAttributeNames.addAll(arc.getAttributeNames());
		}
		arcAttributeNames = new ArrayList<>(new TreeSet<>(arcAttributeNames));

		return "Nodes: " + graph.getNodes().size() + " -- Arcs: " + graph.getArcs().size() + "\nNodeAttributeNames " + nodeAttributeNames + "\nArcAttributeNames " + arcAttributeNames;
	}

	/**
	 * Normalize a text for the PARSE project (remove all non alphabetic characters)
	 *
	 * @param text the input text
	 * @return the normalized text
	 */
	public static String normalize(String text) {
		StringBuilder build = new StringBuilder();
		for (char c : text.toCharArray()) {
			if (Character.isWhitespace(c) || Character.isAlphabetic(c)) {
				build.append(c);
			}
		}
		return build.toString();
	}
}
