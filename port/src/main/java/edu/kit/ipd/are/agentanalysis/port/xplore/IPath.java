package edu.kit.ipd.are.agentanalysis.port.xplore;

import java.io.Serializable;

import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;

/**
 * This interface defines a path in the exploration tree of hypotheses.
 *
 * @author Dominik Fuchss
 *
 */
public interface IPath extends Serializable {
	/**
	 * Get a readable version of the path.
	 *
	 * @return a readable version
	 */
	String getPrettyString();

	/**
	 * Get the path as array of layer entries starting at root.
	 *
	 * @return the path as layer entries
	 */
	ILayerEntry[] getPath();

	/**
	 * Convert the path to {@link IExplorationResult}.
	 *
	 * @param text the text to use for {@link IExplorationResult#getInputText()}
	 * @return the {@link IExplorationResult} which only contains the path
	 */
	IExplorationResult toExplorationResult(String text);
}
