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
	 * Get the input text resp. id for exploration.
	 *
	 * @return the input text or id
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
