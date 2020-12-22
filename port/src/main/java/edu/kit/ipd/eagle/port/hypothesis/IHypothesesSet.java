package edu.kit.ipd.eagle.port.hypothesis;

import java.io.Serializable;
import java.util.List;

/**
 * This interface defines a set of {@link IHypothesis Hypotheses}.
 *
 * @author Dominik Fuchss
 *
 */
public interface IHypothesesSet extends Serializable {
	/**
	 * Get all hypotheses of the group ordered by {@link IHypothesis#getConfidence()
	 * confidence} descending.
	 *
	 * @return a sorted list of hypotheses
	 */
	List<IHypothesis> getHypotheses();

	/**
	 * Indicates whether only one hypothesis of the group can be valid.
	 *
	 * @return the indicator for exclusive validity of one hypothesis
	 */
	boolean isOnlyOneHypothesisValid();

	/**
	 * Get some pretty printed basic information about this hypotheses set.
	 *
	 * @return some pretty printed information
	 */
	String getShortInfo();

	/**
	 * Identify the word (iff existing) which is related to theses hypotheses. May
	 * be {@code null} iff no such word exists.
	 *
	 * @return the word which belong to the hypotheses
	 * @see #getHypothesesRange()
	 */
	String getWordOfHypotheses();

	/**
	 * Get the range of these hypotheses.
	 *
	 * @return the range
	 */
	HypothesisRange getHypothesesRange();

}
