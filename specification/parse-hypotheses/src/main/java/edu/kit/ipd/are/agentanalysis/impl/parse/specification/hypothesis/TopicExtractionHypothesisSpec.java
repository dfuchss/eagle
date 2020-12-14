package edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgent;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.agents.MultiHypothesesTopicExtraction;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.TopicExtractionSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;

/**
 * Defines the agent specification for the
 * {@link MultiHypothesesTopicExtraction}. This is the hypotheses realization
 * for {@link TopicExtractionSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class TopicExtractionHypothesisSpec extends TopicExtractionSpec implements IAgentHypothesisSpecification<PARSEAgent, PARSEGraphWrapper> {
	private int maxHypotheses;

	/**
	 * Create the specification by using the default amount of hypotheses.
	 */
	public TopicExtractionHypothesisSpec() {
		this(IAgentHypothesisSpecification.DEFAULT_HYPOTHESES);
	}

	/**
	 * Create the specification by using a specific amount of hypotheses.
	 *
	 * @param maxHypotheses the specific maximum of generated hypotheses per
	 *                      {@link IHypothesesSet}
	 */
	public TopicExtractionHypothesisSpec(int maxHypotheses) {
		super();
		this.maxHypotheses = maxHypotheses;
	}

	@Override
	public List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyHypothesesToDataStructure(PARSEGraphWrapper data, List<IHypothesesSelection> hypotheses) {
		// TODO Auto-generated method stub

	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.SENTENCE;
	}
}
