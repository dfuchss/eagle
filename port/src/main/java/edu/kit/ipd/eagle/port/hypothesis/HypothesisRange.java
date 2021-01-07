package edu.kit.ipd.eagle.port.hypothesis;

/**
 * Defines the different ranges to which a hypothesis can belong.
 *
 * @author Dominik Fuchss
 *
 */
public enum HypothesisRange {
	/**
	 * Defines Hypotheses which belong to a single element and not to a whole input
	 * (e.g. a word).
	 */
	ELEMENT,
	/**
	 * Defines Hypotheses which belong to a whole input (e.g. a section of a text).
	 */
	INPUT
}
