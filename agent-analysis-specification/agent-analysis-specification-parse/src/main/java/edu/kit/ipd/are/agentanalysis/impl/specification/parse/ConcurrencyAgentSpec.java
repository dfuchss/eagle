package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import static edu.kit.ipd.are.agentanalysis.port.InformationId.ACTIONS;
import static edu.kit.ipd.are.agentanalysis.port.InformationId.CONCURRENCY;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.parse.concurrency.ConcurrencyAgent;

/**
 * Defines the agent specification for the {@link ConcurrencyAgent}.
 *
 * @author Dominik Fuchss
 *
 */
public class ConcurrencyAgentSpec extends ParseAgentSpecification<ConcurrencyAgent> {
	/**
	 * Create the specification.
	 */
	public ConcurrencyAgentSpec() {
		super(new ConcurrencyAgent());
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of(ACTIONS);
	}

	@Override
	public List<InformationId> getProvideIds() {
		return List.of(CONCURRENCY);
	}
}
