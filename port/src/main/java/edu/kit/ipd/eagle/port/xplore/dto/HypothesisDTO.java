package edu.kit.ipd.eagle.port.xplore.dto;

import java.util.Objects;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;

/**
 * Defines the data transfer object for {@link IHypothesis}.
 *
 * @author Dominik Fuchss
 *
 */
public final class HypothesisDTO implements IHypothesis {

	private static final long serialVersionUID = 7281952649169155172L;

	private String prettyInformation;
	private String value;
	private double confidence;

	/**
	 * Create new {@link HypothesisDTO}.
	 */
	public HypothesisDTO() {
	}

	/**
	 * Create a copy of {@link HypothesisDTO}.
	 *
	 * @param hypothesis the hypothesis to copy
	 */
	public HypothesisDTO(HypothesisDTO hypothesis) {
		super();
		this.prettyInformation = hypothesis.prettyInformation;
		this.value = hypothesis.value;
		this.confidence = hypothesis.confidence;
	}

	@Override
	public String getPrettyInformation() {
		return this.prettyInformation;
	}

	/**
	 * Setter for {@link #getPrettyInformation()}.
	 *
	 * @param prettyInformation some pretty readable information on this hypothesis.
	 */
	public void setPrettyInformation(String prettyInformation) {
		this.prettyInformation = prettyInformation;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	/**
	 * Setter for {@link #getValue()}.
	 *
	 * @param value the actual value of the hypothesis
	 */
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public double getConfidence() {
		return this.confidence;
	}

	/**
	 * Setter for {@link #getConfidence()}.
	 *
	 * @param confidence the actual confidence of this hypothesis
	 */
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	@Override
	public String toString() {
		return "Hypothesis(" + this.value + ": " + this.confidence + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.confidence);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		HypothesisDTO other = (HypothesisDTO) obj;
		if (Double.doubleToLongBits(this.confidence) != Double.doubleToLongBits(other.confidence)) {
			return false;
		}
		return Objects.equals(this.value, other.value);
	}

}
