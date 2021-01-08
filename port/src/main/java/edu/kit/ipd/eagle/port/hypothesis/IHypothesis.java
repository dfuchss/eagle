package edu.kit.ipd.eagle.port.hypothesis;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Defines a simple hypothesis. <b>Annotate all your public getters that are not
 * defined here with {@link JsonIgnore}!</b>
 *
 * @author Dominik Fuchss
 *
 */
public interface IHypothesis extends Comparable<IHypothesis>, Serializable {
	/**
	 * Get a readable representation of the hypothesis.
	 *
	 * @return a representation of the hypothesis
	 */
	String getPrettyInformation();

	/**
	 * Get a readable representation of the hypothesis value.
	 *
	 * @return a representation of the hypothesis value
	 */
	String getValue();

	/**
	 * Get the confidence of the hypothesis. Can be anything between
	 * {@link Double#NEGATIVE_INFINITY} and {@link Double#POSITIVE_INFINITY}.
	 * {@link Double#NaN} represents no confidence.
	 *
	 * @return the confidence value of the hypothesis
	 */
	double getConfidence();

	/**
	 * Compare {@link IHypothesis} by their {@link #getConfidence() confidence}
	 * descending.
	 *
	 * @param o the other hypothesis
	 * @return a negative integer, zero, or a positive integer as this hypothesis is
	 *         less than, equal to, or greater than the specified hypothesis.
	 */
	@Override
	default int compareTo(IHypothesis o) {
		return -1 * Double.compare(this.getConfidence(), o.getConfidence());
	}

}
