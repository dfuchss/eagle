package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.pronat.babelfy_wsd.Wsd;

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
