package edu.kit.ipd.eagle.impl.parse.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.parse.PARSEInformationId;
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
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.ACTIONS);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of();
	}
}
