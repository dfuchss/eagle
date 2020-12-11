package edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEInformationId;
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
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.WORD_SENSE_DISAMBIGUATION);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of();
	}
}
