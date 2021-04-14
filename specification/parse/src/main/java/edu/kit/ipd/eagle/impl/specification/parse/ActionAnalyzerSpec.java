package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.pronat.action_analyzer.ActionAnalyzer;
import edu.kit.ipd.pronat.context.ActionRecognizer;

/**
 * Defines the agent specification for the {@link ActionRecognizer}.
 *
 * @author Dominik Fuchss
 *
 */
public class ActionAnalyzerSpec extends ParseAgentSpecification<ActionAnalyzer> {
	/**
	 * Create the specification.
	 */
	public ActionAnalyzerSpec() {
		super(new ActionAnalyzer());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.ACTIONS);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of();
	}
}
