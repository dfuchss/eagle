package edu.kit.ipd.eagle.impl.xplore.selection;

import java.util.List;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.port.hypothesis.HypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.eagle.port.xplore.selection.ISelectionProvider;

/**
 * Simply use the best hypothesis as truth.
 *
 * @author Dominik Fuchss
 *
 */
public class NoExploration implements ISelectionProvider {

    @Override
    public List<List<IHypothesesSelection>> findSelection(List<IHypothesesSet> hypotheses) {
        return List.of(//
                hypotheses.stream() //
                        .filter(hs -> !hs.getHypotheses().isEmpty()) //
                        .map(this::createSelection) //
                        .collect(Collectors.toList()));
    }

    private IHypothesesSelection createSelection(IHypothesesSet hypotheses) {
        List<IHypothesis> hyps = hypotheses.getHypotheses();
        double confidence = hyps.get(0).getConfidence();
        boolean onlyOneConfidence = true;
        for (IHypothesis h : hyps) {
            if (Math.abs(h.getConfidence() - confidence) > 1E-8) {
                onlyOneConfidence = false;
                break;
            }
        }

        if (onlyOneConfidence) {
            return new HypothesesSelection(hypotheses, List.of(hyps.get(0)));
        }

        return new HypothesesSelection(hypotheses, List.of(hypotheses.getSortedHypotheses().get(0)));
    }
}
