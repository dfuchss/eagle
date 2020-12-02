package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.COREF);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of(InformationId.CONTEXT);
	}

}
