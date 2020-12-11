package edu.kit.ipd.are.agentanalysis.impl.parse.agents;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.GraphUtils;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.TokenHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesManager;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesis;
import edu.kit.ipd.parse.luna.data.MissingDataException;
import edu.kit.ipd.parse.luna.graph.INode;
import edu.kit.ipd.parse.luna.graph.INodeType;
import edu.kit.ipd.parse.wikiWSD.WordSenseDisambiguation;
import edu.kit.ipd.parse.wikiWSDClassifier.Classification;
import weka.core.Instance;

/**
 * A implementation of {@link WordSenseDisambiguation} with
 * {@link IHypothesesManager}.
 *
 * @author Dominik Fuchss
 *
 */
public class MultiHypothesesWikiWSD extends WordSenseDisambiguation implements IHypothesesManager<PARSEGraphWrapper> {
	private final int maxHypothesis;
	private static final String MULTI_HYPOTHESIS_WSD_NODE_ATTR = "MHWN-Attr";

	/**
	 * Create the agent based on the max amount of hypotheses per hypotheses set.
	 *
	 * @param maxHypothesis the max amount of hypotheses per set
	 */
	public MultiHypothesesWikiWSD(int maxHypothesis) {
		this.maxHypothesis = Math.max(maxHypothesis, 1);
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.NODE;
	}

	@Override
	protected void prepareGraph() {
		super.prepareGraph();
		INodeType tokenType = this.graph.getNodeType(WordSenseDisambiguation.TOKEN_NODE_TYPE);
		if (!tokenType.containsAttribute(MultiHypothesesWikiWSD.MULTI_HYPOTHESIS_WSD_NODE_ATTR, "String")) {
			tokenType.addAttributeToType("String", MultiHypothesesWikiWSD.MULTI_HYPOTHESIS_WSD_NODE_ATTR);
		}
	}

	@Override
	protected Classification classifyCompoundNoun(Instance instance, String lemma, INode node) {
		List<Classification> classifications = this.classifierService.classifyInstanceWithLemma(instance, lemma, this.maxHypothesis);
		this.storeClassificationToNode(classifications, node);
		return super.classifyCompoundNoun(instance, lemma, node);
	}

	private void storeClassificationToNode(List<Classification> classifications, INode node) {
		node.setAttributeValue(MultiHypothesesWikiWSD.MULTI_HYPOTHESIS_WSD_NODE_ATTR, classifications.toString());
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper graph) {
		List<IHypothesesSet> hyps = new ArrayList<>();
		try {
			for (INode node : WordSenseDisambiguation.getNodesInOrder(graph.getGraph())) {
				String obj = (String) node.getAttributeValue(MultiHypothesesWikiWSD.MULTI_HYPOTHESIS_WSD_NODE_ATTR);
				if (obj == null) {
					continue;
				}
				hyps.add(this.parseHyps(graph, node, obj));
			}
		} catch (MissingDataException e) {
			e.printStackTrace();
		}
		return hyps;
	}

	@Override
	public List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper graph) {
		List<IHypothesesSet> hyps = new ArrayList<>();
		try {
			for (INode node : WordSenseDisambiguation.getNodesInOrder(graph.getGraph())) {
				String hypothesis = (String) node.getAttributeValue(WordSenseDisambiguation.WSD_ATTRIBUTE_NAME);
				if (hypothesis == null) {
					continue;
				}
				hyps.add(new TokenHypothesesSet(graph, node, List.of(new WSDHypothesis(hypothesis, Double.NaN)), true));
			}
		} catch (MissingDataException e) {
			e.printStackTrace();
		}
		return hyps;
	}

	@Override
	public void applyHypothesesToDataStructure(PARSEGraphWrapper graph, List<IHypothesesSelection> selection) {
		this.checkSelection(selection);

		INodeType tokenType;
		if (graph.getGraph().hasNodeType(WordSenseDisambiguation.TOKEN_NODE_TYPE)) {
			tokenType = graph.getGraph().getNodeType(WordSenseDisambiguation.TOKEN_NODE_TYPE);
		} else {
			tokenType = graph.getGraph().createNodeType(WordSenseDisambiguation.TOKEN_NODE_TYPE);
		}
		if (!tokenType.containsAttribute(WordSenseDisambiguation.WSD_ATTRIBUTE_NAME, "String")) {
			tokenType.addAttributeToType("String", WordSenseDisambiguation.WSD_ATTRIBUTE_NAME);
		}

		for (IHypothesesSelection s : selection) {
			TokenHypothesesSet g = (TokenHypothesesSet) s.getAllHypotheses();
			WSDHypothesis h = (WSDHypothesis) s.getSelectedHypotheses().get(0);
			INode node = GraphUtils.getNodeFromClonedGraph(graph.getGraph(), g.getGraphOfHypotheses(), g.getNodeOfHypotheses());
			node.setAttributeValue(WordSenseDisambiguation.WSD_ATTRIBUTE_NAME, h.toClassificationString());
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

	private TokenHypothesesSet parseHyps(PARSEGraphWrapper graph, INode node, String in) {
		List<WSDHypothesis> hyps = new ArrayList<>();
		String data = in.substring(1, in.length() - 1); // Remove '[',']'
		for (String h : data.trim().split(",")) {
			hyps.add(this.toWsdHypothesis(h));
		}

		return new TokenHypothesesSet(graph, node, hyps, true);
	}

	private WSDHypothesis toWsdHypothesis(String hyp) {
		String[] nameXProb = this.splitHypothesis(hyp);
		String name = nameXProb[0];
		double p = Double.parseDouble(nameXProb[1]);
		return new WSDHypothesis(name, p);
	}

	private String[] splitHypothesis(String hyp) {
		for (int i = hyp.length() - 1; i >= 0; i--) {
			if (hyp.charAt(i) == '(') {
				return new String[] { hyp.substring(0, i).trim(), hyp.substring(i + 1, hyp.length() - 1).trim() };
			}
		}
		throw new IllegalArgumentException("Hypothesis string is invalid");
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
