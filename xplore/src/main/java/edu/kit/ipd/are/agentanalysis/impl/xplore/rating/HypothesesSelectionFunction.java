package edu.kit.ipd.are.agentanalysis.impl.xplore.rating;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import edu.kit.ipd.are.agentanalysis.impl.xplore.LayeredExploration;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesis;
import edu.kit.ipd.are.agentanalysis.port.xplore.layer.ILayerEntry;

/**
 * This enum defines selection methods for hypotheses which can be used in
 * {@link LayeredExploration}.
 *
 * @author Dominik Fuchss
 *
 */
public enum HypothesesSelectionFunction {
	/**
	 * Select only hypotheses that were selected by
	 * {@link ILayerEntry#getSelectionsFromBefore()}.
	 */
	USE_SELECTION_FROM_BEFORE(HypothesesSelectionFunction::selectBySelectionFromBefore),
	/**
	 * Select all hypotheses at a specified layer entry.
	 */
	USE_HYPOTHESES(HypothesesSelectionFunction::selectByHypotheses);

	private BiFunction<ILayerEntry[], Integer, List<IHypothesis>> selector;

	/**
	 * Create {@link HypothesesSelectionFunction} by selector for
	 * {@link #select(ILayerEntry[], int)}.
	 *
	 * @param selector the selector
	 */
	HypothesesSelectionFunction(BiFunction<ILayerEntry[], Integer, List<IHypothesis>> selector) {
		this.selector = selector;
	}

	/**
	 * Select hypotheses in a path at a specified index.
	 *
	 * @param path the path
	 * @param idx  the index
	 * @return the hypotheses to mention
	 */
	public List<IHypothesis> select(ILayerEntry[] path, int idx) {
		return this.selector.apply(path, idx);
	}

	private static List<IHypothesis> selectByHypotheses(ILayerEntry[] path, int idx) {

		List<IHypothesis> hypotheses = null;
		if (path[idx].getHypotheses() != null) {
			hypotheses = path[idx].getHypotheses().stream().flatMap(hs -> hs.getHypotheses().stream()).collect(Collectors.toList());
		}
		return hypotheses;
	}

	private static List<IHypothesis> selectBySelectionFromBefore(ILayerEntry[] path, int idx) {
		List<IHypothesis> hypotheses = null;
		if (idx != path.length - 1) {
			if (path[idx + 1].getSelectionsFromBefore() != null) {
				hypotheses = path[idx + 1].getSelectionsFromBefore().stream().flatMap(s -> s.getSelectedHypotheses().stream()).collect(Collectors.toList());
			}
		} else {
			if (path[idx].getHypotheses() != null) {
				hypotheses = path[idx].getHypotheses().stream().flatMap(hs -> hs.getHypotheses().stream()).collect(Collectors.toList());
			}
		}
		return hypotheses;
	}
}
