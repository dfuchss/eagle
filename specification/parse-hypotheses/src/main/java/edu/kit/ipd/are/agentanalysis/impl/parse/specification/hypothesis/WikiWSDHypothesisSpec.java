package edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis;

import edu.kit.ipd.are.agentanalysis.impl.parse.agents.MultiHypothesesWikiWSD;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.WikiWSDSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;

/**
 * Defines the agent specification for the {@link MultiHypothesesWikiWSD}. This
 * is the hypotheses realization for {@link WikiWSDSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class WikiWSDHypothesisSpec extends AbstractAgentHypothesisSpecification<MultiHypothesesWikiWSD> {
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
		super(new WikiWSDSpec(), new MultiHypothesesWikiWSD(maxHypotheses));
	}
}
