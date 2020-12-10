package edu.kit.ipd.are.agentanalysis.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.specification.indirect.IndirectAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.ENTITIES);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of(InformationId.DEPENDENCIES, InformationId.WORD_SENSE_DISAMBIGUATION);
	}

}
