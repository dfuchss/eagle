package edu.kit.ipd.eagle.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEInformationId;
import edu.kit.ipd.eagle.impl.platforms.parse.specification.indirect.IndirectAgentSpecification;
import edu.kit.ipd.indirect.entityrecog.EntityRecognizer;

/**
 * Defines the agent specification for the {@link EntityRecognizer}.
 *
 * @author Dominik Fuchss
 *
 */
public class EntityRecognizerSpec extends IndirectAgentSpecification<EntityRecognizer> {
	/**
	 * Create the specification.
	 */
	public EntityRecognizerSpec() {
		super(new EntityRecognizer());
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.ENTITIES);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of(PARSEInformationId.DEPENDENCIES, PARSEInformationId.WORD_SENSE_DISAMBIGUATION);
	}
}
