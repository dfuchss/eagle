package edu.kit.ipd.eagle.impl.parse.specification.indirect;

import java.util.List;

import edu.kit.ipd.eagle.impl.parse.PARSEInformationId;
import edu.kit.ipd.indirect.constparser.ConstParser;

/**
 * Defines the agent specification for the {@link ConstParser}.
 *
 * @author Dominik Fuchss
 *
 */
public class ConstParserSpec extends IndirectAgentSpecification<ConstParser> {
	/**
	 * Create the specification.
	 */
	public ConstParserSpec() {
		super(new ConstParser());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.CONSTITUENCY);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of();
	}
}
