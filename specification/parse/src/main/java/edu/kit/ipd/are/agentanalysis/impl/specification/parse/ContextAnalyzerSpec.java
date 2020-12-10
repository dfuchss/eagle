package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.CONTEXT);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of();
	}

}
