package edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEInformationId;
import edu.kit.ipd.parse.loop.LoopDetectionAgent;

/**
 * Defines the agent specification for the {@link LoopDetectionAgent}.
 *
 * @author Dominik Fuchss
 *
 */
public class LoopDetectionAgentSpec extends ParseAgentSpecification<LoopDetectionAgent> {
	/**
	 * Create the specification.
	 */
	public LoopDetectionAgentSpec() {
		super(new LoopDetectionAgent());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.LOOP);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of(PARSEInformationId.ACTIONS);
	}
}
