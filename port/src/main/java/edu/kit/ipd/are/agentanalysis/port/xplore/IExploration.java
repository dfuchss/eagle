package edu.kit.ipd.are.agentanalysis.port.xplore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.IDataStructure;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;

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
	 * @param initial the new initial data structure
	 */
	void restart(DS initial);

	/**
	 * Explore the current data structure.
	 *
	 * @return the exploration result
	 */
	IExplorationResult explore();
}
