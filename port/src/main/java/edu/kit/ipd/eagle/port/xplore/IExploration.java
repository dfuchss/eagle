package edu.kit.ipd.eagle.port.xplore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.eagle.port.IAgentSpecification;
import edu.kit.ipd.eagle.port.IDataStructure;
import edu.kit.ipd.eagle.port.hypothesis.IAgentHypothesisSpecification;

/**
 * Defines an explorator which analyzes {@link IAgentSpecification} and
 * {@link IAgentHypothesisSpecification} to generate the different paths of the
 * exploration.
 *
 * @param <DS> the data structure
 * @author Dominik Fuchss
 */
public interface IExploration<DS extends IDataStructure<DS>> {
	/**
	 * The default logger for the exploration.
	 */
	Logger logger = LoggerFactory.getLogger(IExploration.class);

	/**
	 * Restart the exploration with a new data structure.
	 *
	 * @param initial         the new initial data structure
	 * @param id the value for
	 *                        {@link IExplorationResult#getId()}. May be an
	 *                        identifier for the current exploration.
	 */
	void restart(DS initial, String id);

	/**
	 * Explore the current data structure.
	 *
	 * @return the exploration result
	 */
	IExplorationResult explore();
}
