package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.parse.topic_extraction.TopicExtraction;

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
        super(TopicExtraction::new);
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
