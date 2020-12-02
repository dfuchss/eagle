package edu.kit.ipd.are.agentanalysis.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.CONSTITUENCY);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of();
	}

}
