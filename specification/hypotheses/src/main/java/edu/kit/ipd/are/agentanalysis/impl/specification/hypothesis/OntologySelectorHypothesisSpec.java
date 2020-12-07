package edu.kit.ipd.are.agentanalysis.impl.specification.hypothesis;

import edu.kit.ipd.are.agentanalysis.impl.specification.MultiHypothesesOntologySelector;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;

/**
 * Defines the agent specification for the
 * {@link MultiHypothesesOntologySelector}. This is the hypotheses realization
 * for {@link OntologySelectorSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class OntologySelectorHypothesisSpec extends AbstractAgentHypothesisSpecification<MultiHypothesesOntologySelector> {
	/**
	 * Create the specification by using the default amount of hypotheses.
	 */
	public OntologySelectorHypothesisSpec() {
		this(AbstractAgentHypothesisSpecification.DEFAULT_HYPOTHESES);
	}

	/**
	 * Create the specification by using a specific amount of hypotheses.
	 *
	 * @param maxHypotheses the specific maximum of generated hypotheses per
	 *                      {@link IHypothesesSet}
	 */
	public OntologySelectorHypothesisSpec(int maxHypotheses) {
		super(new OntologySelectorSpec(), new MultiHypothesesOntologySelector(maxHypotheses));
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.SENTENCE;
	}

}