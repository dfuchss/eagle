package edu.kit.ipd.are.agentanalysis.impl.specification.indirect;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
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
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.CONCEPTS);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of(InformationId.ENTITIES);
	}

}
