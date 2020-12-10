package edu.kit.ipd.are.agentanalysis.impl.specification.hypothesis;

import edu.kit.ipd.are.agentanalysis.impl.agents.MultiHypothesesTopicExtraction;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis.AbstractAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.TopicExtractionSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;

/**
 * Defines the agent specification for the
 * {@link MultiHypothesesTopicExtraction}. This is the hypotheses realization
 * for {@link TopicExtractionSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class TopicExtractionHypothesisSpec extends AbstractAgentHypothesisSpecification<MultiHypothesesTopicExtraction> {
	/**
	 * Create the specification by using the default amount of hypotheses.
	 */
	public TopicExtractionHypothesisSpec() {
		this(AbstractAgentHypothesisSpecification.DEFAULT_HYPOTHESES);
	}

	/**
	 * Create the specification by using a specific amount of hypotheses.
	 *
	 * @param maxHypotheses the specific maximum of generated hypotheses per
	 *                      {@link IHypothesesSet}
	 */
	public TopicExtractionHypothesisSpec(int maxHypotheses) {
		super(new TopicExtractionSpec(), new MultiHypothesesTopicExtraction(maxHypotheses));
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.SENTENCE;
	}

}