package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.parse.wsd.Wsd;

/**
 * Defines the agent specification for the {@link Wsd WsdAgent}.
 *
 * @author Dominik Fuchss
 *
 */
public class WsdSpec extends ParseAgentSpecification<Wsd> {
	/**
	 * Create the specification.
	 */
	public WsdSpec() {
		super(new Wsd());
	}

	@Override
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.WORD_SENSE_DISAMBIGUATION);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of();
	}

}
