package edu.kit.ipd.eagle.port.xplore.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.kit.ipd.eagle.port.hypothesis.HypothesisRange;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;

/**
 * Defines the data transfer object for {@link IHypothesesSet}.
 *
 * @author Dominik Fuchss
 *
 */
public final class HypothesesSetDTO implements IHypothesesSet {

	private static final long serialVersionUID = 8262033385471417604L;

	private List<IHypothesis> hypotheses;
	private boolean onlyOneHypothesisValid;
	private String shortInfo;
	private String elementOfHypotheses;
	private HypothesisRange range;

	@Override
	public List<IHypothesis> getHypotheses() {
		return this.hypotheses;
	}

	/**
	 * Setter for {@link #getSortedHypotheses()}.
	 *
	 * @param hypotheses the hypotheses of this set
	 */
	@JsonDeserialize(contentAs = HypothesisDTO.class)
	public void setHypotheses(List<IHypothesis> hypotheses) {
		this.hypotheses = hypotheses;
	}

	@Override
	public boolean isOnlyOneHypothesisValid() {
		return this.onlyOneHypothesisValid;
	}

	/**
	 * Setter for {@link #isOnlyOneHypothesisValid()}.
	 *
	 * @param onlyOneHypothesisValid indicator whether only one hypothesis can be valid
	 */
	public void setOnlyOneHypothesisValid(boolean onlyOneHypothesisValid) {
		this.onlyOneHypothesisValid = onlyOneHypothesisValid;
	}

	@Override
	public String getShortInfo() {
		return this.shortInfo;
	}

	/**
	 * Setter for {@link #getShortInfo()}.
	 *
	 * @param shortInfo some short readable information on this set of hypotheses
	 */
	public void setShortInfo(String shortInfo) {
		this.shortInfo = shortInfo;
	}

	@Override
	public String getElementOfHypotheses() {
		return this.elementOfHypotheses;
	}

	/**
	 * Setter for {@link #getElementOfHypotheses()}.
	 *
	 * @param elementOfHypotheses the word which belong to that set of hypotheses
	 */
	public void setElementOfHypotheses(String elementOfHypotheses) {
		this.elementOfHypotheses = elementOfHypotheses;
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return this.range;
	}

	/**
	 * Setter for {@link #getHypothesesRange()}.
	 *
	 * @param range the range of this set of hypotheses
	 */
	public void setHypothesesRange(HypothesisRange range) {
		this.range = range;
	}

	@Override
	public IHypothesesSet withHypotheses(List<IHypothesis> hypotheses) {
		throw new UnsupportedOperationException("NIY");
	}
}
