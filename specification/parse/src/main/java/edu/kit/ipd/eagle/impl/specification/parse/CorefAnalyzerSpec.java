package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.pronat.coref.CorefAnalyzer;

/**
 * Defines the agent specification for the {@link CorefAnalyzer}.
 *
 * @author Dominik Fuchss
 *
 */
public class CorefAnalyzerSpec extends ParseAgentSpecification<CorefAnalyzer> {
    /**
     * Create the specification.
     */
    public CorefAnalyzerSpec() {
        super(CorefAnalyzer::new);
    }

    @Override
    public List<PARSEInformationId> getProvideIds() {
        return List.of(PARSEInformationId.COREF);
    }

    @Override
    public List<PARSEInformationId> getRequiresIds() {
        return List.of(PARSEInformationId.CONTEXT);
    }
}
