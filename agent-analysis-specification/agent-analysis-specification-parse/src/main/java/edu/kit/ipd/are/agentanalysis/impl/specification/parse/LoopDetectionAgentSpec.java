package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.LOOP);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of(InformationId.ACTIONS);
	}

}
