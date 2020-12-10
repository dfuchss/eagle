package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.CONDITIONS);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of();
	}

}
