package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.pronat.loop.LoopDetectionAgent;

/**
 * Defines the agent specification for the {@link LoopDetectionAgent}.
 *
 * @author Dominik Fuchss
 *
 */
public class LoopDetectionAgentSpec extends ParseAgentSpecification<LoopDetectionAgent> {
    /**
     * Create the specification.
     */
    public LoopDetectionAgentSpec() {
        super(LoopDetectionAgent::new);
    }

    @Override
    public List<PARSEInformationId> getProvideIds() {
        return List.of(PARSEInformationId.LOOP);
    }

    @Override
    public List<PARSEInformationId> getRequiresIds() {
        return List.of(PARSEInformationId.ACTIONS);
    }
}
