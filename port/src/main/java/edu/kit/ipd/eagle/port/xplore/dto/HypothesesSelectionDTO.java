package edu.kit.ipd.eagle.port.xplore.dto;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;

/**
 * Defines the data transfer object for {@link IHypothesesSelection}.
 *
 * @author Dominik Fuchss
 *
 */
public class HypothesesSelectionDTO implements IHypothesesSelection {

	private static final long serialVersionUID = 6243710075382876265L;

	private IHypothesesSet allHypotheses;
	private List<IHypothesis> selectedHypotheses;

	@Override
	public IHypothesesSet getAllHypotheses() {
		return this.allHypotheses;
	}

	/**
	 * Setter for {@link #getAllHypotheses()}.
	 *
	 * @param allHypotheses the hypotheses set of this selection
	 */
	@JsonDeserialize(as = HypothesesSetDTO.class)
	public void setAllHypotheses(IHypothesesSet allHypotheses) {
		this.allHypotheses = allHypotheses;
	}

	@Override
	public List<IHypothesis> getSelectedHypotheses() {
		return this.selectedHypotheses;
	}

	/**
	 * Setter for {@link #getSelectedHypotheses()}.
	 *
	 * @param selectedHypotheses the selected hypotheses of this selection
	 */
	@JsonDeserialize(contentAs = HypothesisDTO.class)
	public void setSelectedHypotheses(List<IHypothesis> selectedHypotheses) {
		this.selectedHypotheses = selectedHypotheses;
	}

	@Override
	public String toString() {
		return "HypothesesSelection [selection=" + this.selectedHypotheses + "]";
	}

}
