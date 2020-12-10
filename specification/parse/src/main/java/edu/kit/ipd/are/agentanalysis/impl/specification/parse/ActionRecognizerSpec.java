package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.parse.actionRecognizer.ActionRecognizer;

/**
 * Defines the agent specification for the {@link ActionRecognizer}.
 *
 * @author Dominik Fuchss
 *
 */
public class ActionRecognizerSpec extends ParseAgentSpecification<ActionRecognizer> {
	/**
	 * Create the specification.
	 */
	public ActionRecognizerSpec() {
		super(new ActionRecognizer());
	}

	@Override
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.ACTIONS);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of();
	}

}
