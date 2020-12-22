package edu.kit.ipd.eagle.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.indirect.IndirectAgentSpecification;
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
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.NER);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of();
	}
}
