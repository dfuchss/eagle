package edu.kit.ipd.are.agentanalysis.port.hypothesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.INode;

/**
 * A very basic implementation of a {@link IHypothesesSet HypothesesSet}. It
 * simply stores a list of hypotheses and the indicator whether only one of them
 * can be valid for a specific selection.
 *
 * @author Dominik Fuchss
 *
 */
public class BasicHypothesesSet implements IHypothesesSet {

	private static final long serialVersionUID = -1851465961928624076L;

	private List<IHypothesis> hypotheses;
	private boolean onlyOneValidHypothesis;
	private String shortInfo;
	private HypothesisRange range;

	// For deserialize
	@SuppressWarnings("unused")
	private BasicHypothesesSet() {
	}

	/**
	 * Create a simple group of hypotheses.
	 *
	 * @param shortInfo  some short info
	 * @param range      the range of the set of hypotheses
	 * @param hypotheses the hypotheses
	 * @param onlyOne    indicator for {@link #isOnlyOneHypothesisValid()}
	 */
	public BasicHypothesesSet(String shortInfo, HypothesisRange range, List<? extends IHypothesis> hypotheses, boolean onlyOne) {
		this.shortInfo = shortInfo;
		this.range = Objects.requireNonNull(range);
		this.hypotheses = new ArrayList<>(hypotheses);
		this.onlyOneValidHypothesis = onlyOne;
	}

	@Override
	public String getShortInfo() {
		return this.shortInfo;
	}

	@Override
	public List<IHypothesis> getHypotheses() {
		var res = new ArrayList<>(this.hypotheses);
		res.sort((a, b) -> -Double.compare(a.getConfidence(), b.getConfidence()));
		return res;
	}

	@Override
	public boolean isOnlyOneHypothesisValid() {
		return this.onlyOneValidHypothesis;
	}

	@Override
	public IGraph getGraphOfHypotheses() {
		return null;
	}

	@Override
	public INode getNodeOfHypotheses() {
		return null;
	}

	@Override
	public String getWordOfHypotheses() {
		return null;
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return this.range;
	}

	@Override
	public String toString() {
		return "Hypotheses: {" + this.shortInfo + "}@" + this.range + " -- [" + this.hypotheses + "]";
	}
}
