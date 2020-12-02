package edu.kit.ipd.are.agentanalysis.port.xplore;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;

/**
 * Defines the result of the exploration process.
 *
 * @author Dominik Fuchss
 *
 */
public interface IExplorationResult extends Serializable {

	/**
	 * Get the input text for exploration.
	 *
	 * @return the input text
	 */
	String getInputText();

	/**
	 * Get the exploration's root.
	 *
	 * @return the root entry
	 */
	ILayerEntry getExplorationRoot();

	/**
	 * Get the paths starting at {@link #getExplorationRoot()}.
	 *
	 * @return all paths
	 */
	@JsonIgnore
	List<IPath> getPaths();

}
