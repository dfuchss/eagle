package edu.kit.ipd.eagle.impl.parse.specification.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import edu.kit.ipd.eagle.impl.parse.PARSEInformationId;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import edu.kit.ipd.parse.ontology_selector.OntologySelector;

/**
 * Defines the agent specification for the {@link OntologySelector}.
 *
 * @author Dominik Fuchss
 *
 */
public class OntologySelectorSpec extends ParseAgentSpecification<OntologySelector> {
	/**
	 * Create the specification.
	 *
	 * @param actorOntologies the actor ontologies see
	 *                        {@link #loadOntologies(String, String, String...)}
	 * @param envOntologies   the environment ontologies see
	 *                        {@link #loadOntologies(String, String, String...)}
	 */
	public OntologySelectorSpec(String actorOntologies, String envOntologies) {
		super(OntologySelectorSpec.loadOntologySelector(actorOntologies, envOntologies));
	}

	private static OntologySelector loadOntologySelector(String actorOntologies, String envOntologies) {
		var props = ConfigManager.getConfiguration(OntologySelector.class);
		props.setProperty("ACTOR_ONTOLOGIES", Objects.requireNonNull(actorOntologies));
		props.setProperty("ENVIRONMENT_ONTOLOGIES", Objects.requireNonNull(envOntologies));
		props.setProperty("OUTPUT", "target/");
		return new OntologySelector();
	}

	/**
	 * Generate an ontology string for multiple ontologies.
	 *
	 * @param base           the base directory with tailing '/'
	 * @param ontology       the first ontology
	 * @param moreOntologies further ontologies
	 * @return a string that defines the loaded ontologies
	 */
	public static String loadOntologies(String base, String ontology, String... moreOntologies) {
		List<String> ontologies = new ArrayList<>(Arrays.asList(moreOntologies));
		ontologies.add(ontology);
		Collections.sort(ontologies);

		Iterator<String> iter = ontologies.iterator();

		StringBuilder result = new StringBuilder();
		result.append(base).append(iter.next());
		while (iter.hasNext()) {
			result.append(",").append(base).append(iter.next());
		}
		return result.toString();
	}

	@Override
	public List<PARSEInformationId> getProvideIds() {
		return List.of(PARSEInformationId.ONTOLOGY);
	}

	@Override
	public List<PARSEInformationId> getRequiresIds() {
		return List.of(PARSEInformationId.TOPICS);
	}
}
