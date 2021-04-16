package edu.kit.ipd.eagle.impl.specification.parse;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.parse.concurrency.ConcurrencyAgent;

/**
 * Defines the agent specification for the {@link ConcurrencyAgent}.
 *
 * @author Dominik Fuchss
 *
 */
public class ConcurrencyAgentSpec extends ParseAgentSpecification<ConcurrencyAgent> {
    /**
     * Create the specification.
     */
    public ConcurrencyAgentSpec() {
        super(ConcurrencyAgent::new);
    }

    @Override
    public List<PARSEInformationId> getRequiresIds() {
        return List.of(PARSEInformationId.ACTIONS);
    }

    @Override
    public List<PARSEInformationId> getProvideIds() {
        return List.of(PARSEInformationId.CONCURRENCY);
    }
}
