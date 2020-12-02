package edu.kit.ipd.are.agentanalysis.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.indirect.textner.TextNERTagger;

/**
 * Defines the agent specification for the {@link TextNERTagger}.
 *
 * @author Dominik Fuchss
 *
 */
public class TextNERSpec extends IndirectAgentSpecification<TextNERTagger> {
	/**
	 * Create the specification.
	 */
	public TextNERSpec() {
		super(new TextNERTagger());
	}

	@Override
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.NER);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of();
	}

}
