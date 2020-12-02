package edu.kit.ipd.are.agentanalysis.port.hypothesis;

import java.io.Serializable;
import java.util.List;

/**
 * This interface defines the minimum of methods needed for a selection of
 * {@link IHypothesis hypotheses} of a {@link IHypothesesSet group of
 * hypotheses}.
 *
 * @author Dominik Fuchss
 *
 */
public interface IHypothesesSelection extends Serializable {
	/**
	 * Get the group of hypotheses for this selection.
	 *
	 * @return the group of selection
	 */
	IHypothesesSet getAllHypotheses();

	/**
	 * Get the selected hypotheses from the group. Each hypothesis which has been
	 * selected is retrieved from {@link IHypothesesSet#getHypotheses()}.
	 *
	 * @return a list of selected hypotheses from the group
	 */
	List<IHypothesis> getSelectedHypotheses();
}
