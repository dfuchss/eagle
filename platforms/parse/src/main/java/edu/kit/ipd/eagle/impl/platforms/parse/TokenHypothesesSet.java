package edu.kit.ipd.eagle.impl.platforms.parse;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.kit.ipd.eagle.port.hypothesis.HypothesisRange;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.INode;

/**
 * Represents a simple set of hypotheses. Thereby, the hypotheses are bound to one specific {@link INode}.
 *
 * @author Dominik Fuchss
 *
 */
public class TokenHypothesesSet implements IHypothesesSet {

	private static final long serialVersionUID = 5660631757349218639L;

	private List<IHypothesis> hypotheses;
	private transient IGraph baseGraph;
	private transient INode baseNode;

	private boolean onlyOneHypothesisValid;

	// For deserialize
	@SuppressWarnings("unused")
	private TokenHypothesesSet() {
	}

	/**
	 * Create a hypotheses group by graph, specific node, hypotheses and indicator for
	 * {@link #isOnlyOneHypothesisValid()}.
	 *
	 * @param baseGraph  the graph of the baseNode
	 * @param baseNode   the node interlinked with the hypotheses
	 * @param hypotheses the hypotheses
	 * @param onlyOne    the indicator for {@link #isOnlyOneHypothesisValid()}
	 */
	public TokenHypothesesSet(IGraph baseGraph, INode baseNode, List<? extends IHypothesis> hypotheses, boolean onlyOne) {
		this.baseGraph = baseGraph;
		this.baseNode = baseNode;
		if (baseNode != null && this.baseNode.getType() != this.baseGraph.getNodeType("token")) {
			throw new IllegalArgumentException("Node is no TOKEN-Node.");
		}

		this.hypotheses = new ArrayList<>(hypotheses);
		this.onlyOneHypothesisValid = onlyOne;
	}

	@Override
	public List<IHypothesis> getHypotheses() {
		return new ArrayList<>(this.hypotheses);
	}

	@Override
	public boolean isOnlyOneHypothesisValid() {
		return this.onlyOneHypothesisValid;
	}

	@Override
	public String getShortInfo() {
		return this.getElementOfHypotheses();
	}

	/**
	 * Get the graph that was used to access the hypotheses.
	 *
	 * @return the original graph
	 */
	@JsonIgnore
	public IGraph getGraphOfHypotheses() {
		return this.baseGraph;
	}

	/**
	 * Get the node that was used to access the hypotheses.
	 *
	 * @return the original node
	 */
	@JsonIgnore
	public INode getNodeOfHypotheses() {
		return this.baseNode;
	}

	@Override
	public String getElementOfHypotheses() {
		return this.baseNode == null ? null : (String) this.baseNode.getAttributeValue("value");
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.ELEMENT;
	}

	@Override
	public String toString() {
		return "Hypotheses: {" + this.getElementOfHypotheses() + "}@" + HypothesisRange.ELEMENT + " -- " + this.hypotheses;
	}

	@Override
	public IHypothesesSet withHypotheses(List<IHypothesis> hypotheses) {
		return new TokenHypothesesSet(baseGraph, baseNode, hypotheses, onlyOneHypothesisValid);
	}
}
