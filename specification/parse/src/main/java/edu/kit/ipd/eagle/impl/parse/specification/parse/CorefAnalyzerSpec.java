package edu.kit.ipd.eagle.impl.parse.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.parse.PARSEInformationId;
import edu.kit.ipd.parse.corefanalyzer.CorefAnalyzer;

/**
 * Defines the agent specification for the {@link CorefAnalyzer}.
 *
 * @author Dominik Fuchss
 *
 */
public class CorefAnalyzerSpec extends ParseAgentSpecification<CorefAnalyzer> {
	/**
	 * Create the specification.
	 */
	public CorefAnalyzerSpec() {
		super(new CorefAnalyzer());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.COREF);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of(PARSEInformationId.CONTEXT);
	}
}
