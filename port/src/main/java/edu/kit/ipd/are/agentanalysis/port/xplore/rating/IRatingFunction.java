package edu.kit.ipd.are.agentanalysis.port.xplore.rating;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.ipd.are.agentanalysis.port.xplore.IPath;

/**
 * Defines a rating function for a set of paths.
 *
 * @author Dominik Fuchss
 *
 */
public interface IRatingFunction {
	/**
	 * The default logger for the rating functions.
	 */
	Logger logger = LoggerFactory.getLogger(IRatingFunction.class);

	/**
	 * Invoke rating by providing a list of paths to be compared and rated.
	 *
	 * @param paths a list of paths
	 * @return a list of scores for each of the input paths (the values depend on
	 *         the kind of rating function)
	 *
	 */
	List<Double> ratePaths(List<IPath> paths);
}
