package edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEInformationId;
import edu.kit.ipd.parse.conditionDetection.ConditionDetector;

/**
 * Defines the agent specification for the {@link ConditionDetector}.
 *
 * @author Dominik Fuchss
 *
 */
public class ConditionDetectorSpec extends ParseAgentSpecification<ConditionDetector> {
	/**
	 * Create the specification.
	 */
	public ConditionDetectorSpec() {
		super(new ConditionDetector());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.CONDITIONS);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of();
	}
}
