package edu.kit.ipd.eagle.port.xplore.layer;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;

/**
 * Defines an entry of an {@link ILayer}. An entry defines a single exploration
 * step and belongs to exactly one layer.
 *
 * @author Dominik Fuchss
 *
 */
public interface ILayerEntry extends Serializable {
	/**
	 * The default logger for the layered exploration entries.
	 */
	Logger logger = LoggerFactory.getLogger(ILayerEntry.class);

	/**
	 * Indicates whether this entry belongs to a layer which has a next layer.
	 *
	 * @return iff {@code true} this entry belongs to a layer with no further layer
	 *         after it.
	 */
	@JsonIgnore
	boolean isLeaf();

	/**
	 * Get the path to the root entry. Will return [this, ..., root] as a list of
	 * layer entries.
	 *
	 * @return a path to the root entry.
	 */
	@JsonIgnore
	ILayerEntry[] getPathToRoot();

	/**
	 * Get the direct children of this layer entry. Each child belongs to an
	 * {@link IHypothesesSelection} in the parent layer entry.
	 *
	 * @return a list of children of this entry
	 */
	List<ILayerEntry> getChildren();

	/**
	 * Get the hypotheses of this layer entry.
	 *
	 * @return the hypotheses of this layer entry
	 */
	List<IHypothesesSet> getHypotheses();

	/**
	 * Get the selections from the parent layer entry for this layer entry.
	 *
	 * @return the selections from the parent entry which lead to this layer entry
	 */
	List<IHypothesesSelection> getSelectionsFromBefore();

	/**
	 * Get the unique id of this step.
	 *
	 * @return the unique id
	 */
	String getId();

	/**
	 * Get the agent used in this step.
	 *
	 * @return the agent's name
	 */
	String getAgent();

}
