package edu.kit.ipd.eagle.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.indirect.IndirectAgentSpecification;
import edu.kit.ipd.indirect.conceptualizer.Conceptualizer;

/**
 * Defines the agent specification for the {@link Conceptualizer}.
 *
 * @author Dominik Fuchss
 *
 */
public class ConceptualizerSpec extends IndirectAgentSpecification<Conceptualizer> {
	/**
	 * Create the specification.
	 */
	public ConceptualizerSpec() {
		super(new Conceptualizer());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.CONCEPTS);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of(PARSEInformationId.ENTITIES);
	}
}
