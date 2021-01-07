package edu.kit.ipd.eagle.port.xplore;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;

/**
 * Defines the result of the exploration process.
 *
 * @author Dominik Fuchss
 *
 */
public interface IExplorationResult extends Serializable {

	/**
	 * Get an identifier for exploration (may be the input text).
	 *
	 * @return the id of the exploration
	 */
	String getId();

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
