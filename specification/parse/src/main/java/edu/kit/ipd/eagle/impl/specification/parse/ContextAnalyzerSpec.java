package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.parse.contextanalyzer.ContextAnalyzer;

/**
 * Defines the agent specification for the {@link ContextAnalyzer}.
 *
 * @author Dominik Fuchss
 *
 */
public class ContextAnalyzerSpec extends ParseAgentSpecification<ContextAnalyzer> {
	/**
	 * Create the specification.
	 */
	public ContextAnalyzerSpec() {
		super(new ContextAnalyzer());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.CONTEXT);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of();
	}
}
