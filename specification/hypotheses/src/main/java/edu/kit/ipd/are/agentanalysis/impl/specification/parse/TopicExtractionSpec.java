package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.TOPICS);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of(InformationId.WIKI_WSD);
	}

}
