package edu.kit.ipd.eagle.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.indirect.IndirectAgentSpecification;
import edu.kit.ipd.indirect.depparser.DepParser;
import edu.kit.ipd.parse.luna.tools.ConfigManager;

/**
 * Defines the agent specification for the {@link DepParser}.
 *
 * @author Dominik Fuchss
 *
 */
public class DepParserSpec extends IndirectAgentSpecification<DepParser> {
    /**
     * Create the specification.
     */
    public DepParserSpec() {
        super(DepParser::new);
        var props = ConfigManager.getConfiguration(DepParser.class);
        props.setProperty("depparse.model", "edu/stanford/nlp/models/parser/nndep/english_UD.gz");
    }

    @Override
    public List<PARSEInformationId> getProvideIds() {
        return List.of(PARSEInformationId.DEPENDENCIES);
    }

    @Override
    public List<PARSEInformationId> getRequiresIds() {
        return List.of();
    }
}
