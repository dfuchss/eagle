package edu.kit.ipd.eagle.port.hypothesis;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a simple {@link IHypothesesSelection}.
 *
 * @author Dominik Fuchss
 *
 */
public final class HypothesesSelection implements IHypothesesSelection {

	private static final long serialVersionUID = 3518575814774155332L;
	private List<IHypothesis> selectedHypotheses;
	private IHypothesesSet hypothesesForSelection;

	/**
	 * Create a new selection of hypotheses.
	 *
	 * @param hypotheses the set of hypotheses for the selection
	 * @param selection  the actual selected hypotheses
	 */
	public HypothesesSelection(IHypothesesSet hypotheses, List<IHypothesis> selection) {
		this.hypothesesForSelection = hypotheses;
		this.selectedHypotheses = new ArrayList<>(selection);
	}

	@Override
	public IHypothesesSet getAllHypotheses() {
		return this.hypothesesForSelection;
	}

	@Override
	public List<IHypothesis> getSelectedHypotheses() {
		return new ArrayList<>(this.selectedHypotheses);
	}

	@Override
	public String toString() {
		return "HypothesesSelection [selection=" + this.selectedHypotheses + "]";
	}

}
