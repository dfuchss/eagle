package edu.kit.ipd.are.agentanalysis.impl.specification.hypothesis;

import edu.kit.ipd.are.agentanalysis.impl.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import im.janke.ontologySelector.MultiHypothesisOntologySelector;

/**
 * Defines the agent specification for the
 * {@link MultiHypothesisOntologySelector}. This is the hypotheses realization
 * for {@link OntologySelectorSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class OntologySelectorHypothesisSpec extends AbstractAgentHypothesisSpecification<MultiHypothesisOntologySelector> {
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
		super(new OntologySelectorSpec(), new MultiHypothesisOntologySelector(maxHypotheses));
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.SENTENCE;
	}

}