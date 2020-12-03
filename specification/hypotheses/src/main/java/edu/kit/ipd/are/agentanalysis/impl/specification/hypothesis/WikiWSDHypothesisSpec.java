package edu.kit.ipd.are.agentanalysis.impl.specification.hypothesis;

import edu.kit.ipd.are.agentanalysis.impl.specification.parse.WikiWSDSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import im.janke.wsdAgent.MultiHypothesisWSD;

/**
 * Defines the agent specification for the {@link MultiHypothesisWSD}. This is
 * the hypotheses realization for {@link WikiWSDSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class WikiWSDHypothesisSpec extends AbstractAgentHypothesisSpecification<MultiHypothesisWSD> {
	/**
	 * Create the specification by using the default amount of hypotheses.
	 */
	public WikiWSDHypothesisSpec() {
		this(AbstractAgentHypothesisSpecification.DEFAULT_HYPOTHESES);
	}

	/**
	 * Create the specification by using a specific amount of hypotheses.
	 *
	 * @param maxHypotheses the specific maximum of generated hypotheses per
	 *                      {@link IHypothesesSet}
	 */
	public WikiWSDHypothesisSpec(int maxHypotheses) {
		super(new WikiWSDSpec(), new MultiHypothesisWSD(maxHypotheses));
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.NODE;
	}

}
