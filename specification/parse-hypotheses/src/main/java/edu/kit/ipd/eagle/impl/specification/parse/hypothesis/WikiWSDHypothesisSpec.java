package edu.kit.ipd.eagle.impl.specification.parse.hypothesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.impl.platforms.parse.GraphUtils;
import edu.kit.ipd.eagle.impl.platforms.parse.PARSEAgent;
import edu.kit.ipd.eagle.impl.platforms.parse.PARSEGraphWrapper;
import edu.kit.ipd.eagle.impl.platforms.parse.TokenHypothesesSet;
import edu.kit.ipd.eagle.impl.specification.parse.WikiWSDSpec;
import edu.kit.ipd.eagle.port.hypothesis.HypothesisRange;
import edu.kit.ipd.eagle.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.INode;
import edu.kit.ipd.parse.luna.graph.INodeType;
import edu.kit.ipd.parse.luna.graph.Pair;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import edu.kit.ipd.parse.wikiWSD.WordSenseDisambiguation;

/**
 * Defines the agent specification for the {@link WordSenseDisambiguation
 * WikiWSD}. This is the hypotheses realization for {@link WikiWSDSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class WikiWSDHypothesisSpec extends WikiWSDSpec implements IAgentHypothesisSpecification<PARSEAgent, PARSEGraphWrapper> {
	private static final String TOKEN_NODE_TYPE = "token";
	private static final String WSD_ATTRIBUTE_NAME = "wsd";
	private static final String WSD_TOP_X_ATTRIBUTE_NAME = "wsd-top-x";

	/**
	 * Create the specification by using the default amount of hypotheses.
	 */
	public WikiWSDHypothesisSpec() {
		this(IAgentHypothesisSpecification.DEFAULT_HYPOTHESES);
	}

	/**
	 * Create the specification by using a specific amount of hypotheses.
	 *
	 * @param maxHypotheses the specific maximum of generated hypotheses per
	 *                      {@link IHypothesesSet}
	 */
	public WikiWSDHypothesisSpec(int maxHypotheses) {
		super();
		// Set Max Hypotheses in Agent's configuration.
		Properties props = ConfigManager.getConfiguration(WordSenseDisambiguation.class);
		props.setProperty("STORE_TOP_X", String.valueOf(maxHypotheses));
	}

	@Override
	public List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper graph) {
		List<IHypothesesSet> hyps = new ArrayList<>();

		for (INode node : WikiWSDHypothesisSpec.getNodesInOrderFast(graph.getGraph())) {
			String hypothesis = (String) node.getAttributeValue(WikiWSDHypothesisSpec.WSD_ATTRIBUTE_NAME);
			if (hypothesis == null) {
				continue;
			}
			hyps.add(new TokenHypothesesSet(graph, node, List.of(new WSDHypothesis(hypothesis, Double.NaN)), true));
		}

		return hyps;
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper graph) {
		List<IHypothesesSet> hyps = new ArrayList<>();

		for (INode node : WikiWSDHypothesisSpec.getNodesInOrderFast(graph.getGraph())) {
			@SuppressWarnings("unchecked")
			List<Pair<String, Double>> obj = //
					(List<Pair<String, Double>>) node.getAttributeValue(WikiWSDHypothesisSpec.WSD_TOP_X_ATTRIBUTE_NAME);
			if (obj == null) {
				continue;
			}
			hyps.add(this.parseHyps(graph, node, obj));
		}

		return hyps;
	}

	@Override
	public void applyHypothesesToDataStructure(PARSEGraphWrapper graph, List<IHypothesesSelection> selection) {
		this.checkSelection(selection);

		INodeType tokenType;
		if (graph.getGraph().hasNodeType(WikiWSDHypothesisSpec.TOKEN_NODE_TYPE)) {
			tokenType = graph.getGraph().getNodeType(WikiWSDHypothesisSpec.TOKEN_NODE_TYPE);
		} else {
			tokenType = graph.getGraph().createNodeType(WikiWSDHypothesisSpec.TOKEN_NODE_TYPE);
		}
		if (!tokenType.containsAttribute(WikiWSDHypothesisSpec.WSD_ATTRIBUTE_NAME, "String")) {
			tokenType.addAttributeToType("String", WikiWSDHypothesisSpec.WSD_ATTRIBUTE_NAME);
		}

		for (IHypothesesSelection s : selection) {
			TokenHypothesesSet g = (TokenHypothesesSet) s.getAllHypotheses();
			WSDHypothesis h = (WSDHypothesis) s.getSelectedHypotheses().get(0);
			INode node = GraphUtils.getNodeFromClonedGraph(graph.getGraph(), g.getGraphOfHypotheses(), g.getNodeOfHypotheses());
			node.setAttributeValue(WikiWSDHypothesisSpec.WSD_ATTRIBUTE_NAME, h.toClassificationString());
		}

	}

	private void checkSelection(List<IHypothesesSelection> selection) {
		for (IHypothesesSelection s : selection) {
			if (!(s.getAllHypotheses() instanceof TokenHypothesesSet)) {
				throw new IllegalArgumentException("Not a valid HypothesesGroup: " + s.getAllHypotheses());
			}
			TokenHypothesesSet g = (TokenHypothesesSet) s.getAllHypotheses();
			for (IHypothesis h : s.getSelectedHypotheses()) {
				if (!(h instanceof WSDHypothesis)) {
					throw new IllegalArgumentException("Not a valid WSDHypothesis: " + h);
				}
			}
			if (s.getSelectedHypotheses().size() > 1) {
				throw new IllegalArgumentException("Too many selected WSDHypothesis for " + g);
			}
		}

	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.ELEMENT;
	}

	private IHypothesesSet parseHyps(PARSEGraphWrapper graph, INode node, List<Pair<String, Double>> hypotheses) {
		List<WSDHypothesis> hyps = hypotheses.stream().map(h -> new WSDHypothesis(h.getLeft(), h.getRight())).collect(Collectors.toList());
		return new TokenHypothesesSet(graph, node, hyps, true);
	}

	private static List<INode> getNodesInOrderFast(IGraph graph) {
		// TODO DTHF1 Check whether this is the right order ..
		return graph.getNodesOfType(graph.getNodeType(WikiWSDHypothesisSpec.TOKEN_NODE_TYPE));
	}

	private static final class WSDHypothesis implements IHypothesis {

		private static final long serialVersionUID = -3925183135254687038L;

		// For deserialize
		private WSDHypothesis() {
		}

		private WSDHypothesis(String value, double score) {
			this.value = value;
			this.score = score;
		}

		private String value;

		private double score;

		@Override
		public String toString() {
			return "Hypothesis [value=" + this.value + ", score=" + this.score + "]";
		}

		@Override
		public String getPrettyInformation() {
			return this.toString();
		}

		@Override
		public double getConfidence() {
			return this.score;
		}

		public String toClassificationString() {
			return this.value;
		}

		@Override
		public String getValue() {
			return this.value;
		}
	}
}
