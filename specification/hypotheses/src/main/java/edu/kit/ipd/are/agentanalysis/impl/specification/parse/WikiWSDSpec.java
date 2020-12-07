package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.parse.wikiWSD.WordSenseDisambiguation;

/**
 * Defines the agent specification for the {@link WordSenseDisambiguation
 * WikiWSD}.
 *
 * @author Dominik Fuchss
 *
 */
public class WikiWSDSpec extends ParseAgentSpecification<WordSenseDisambiguation> {
	/**
	 * Create the specification.
	 */
	public WikiWSDSpec() {
		super(new WordSenseDisambiguation());
	}

	@Override
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.WIKI_WSD);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of();
	}
}
