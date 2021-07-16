package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.pronat.wiki_wsd.WordSenseDisambiguation;

/**
 * Defines the agent specification for the {@link WordSenseDisambiguation WikiWSD}.
 *
 * @author Dominik Fuchss
 *
 */
public class WikiWSDSpec extends ParseAgentSpecification<WordSenseDisambiguation> {
    /**
     * Create the specification.
     */
    public WikiWSDSpec() {
        super(WordSenseDisambiguation::new);
    }

    @Override
    public List<PARSEInformationId> getProvideIds() {
        return List.of(PARSEInformationId.WIKI_WSD);
    }

    @Override
    public List<PARSEInformationId> getRequiresIds() {
        return List.of();
    }
}
