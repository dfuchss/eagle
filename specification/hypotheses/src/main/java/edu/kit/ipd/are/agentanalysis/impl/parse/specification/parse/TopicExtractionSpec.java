package edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEInformationId;
import edu.kit.ipd.parse.topicextraction.TopicExtraction;

/**
 * Defines the agent specification for the {@link TopicExtraction}.
 *
 * @author Dominik Fuchss
 *
 */
public class TopicExtractionSpec extends ParseAgentSpecification<TopicExtraction> {
	/**
	 * Create the specification.
	 */
	public TopicExtractionSpec() {
		super(new TopicExtraction());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.TOPICS);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of(PARSEInformationId.WIKI_WSD);
	}
}
