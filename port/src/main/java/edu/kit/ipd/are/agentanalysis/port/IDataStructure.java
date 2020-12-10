package edu.kit.ipd.are.agentanalysis.port;

/**
 * Defines a data structure for agents.
 *
 * @author Dominik Fuchss
 *
 * @param <DS> just use {@code class Data implements IDataStructure<DS>}
 */
public interface IDataStructure<DS extends IDataStructure<DS>> extends Cloneable {
	/**
	 * Create a clone of the data structure.
	 *
	 * @return a clone of the data structure
	 */
	DS clone();

	/**
	 * Get the original input text.
	 *
	 * @return the original input text
	 */
	String getText();
}
