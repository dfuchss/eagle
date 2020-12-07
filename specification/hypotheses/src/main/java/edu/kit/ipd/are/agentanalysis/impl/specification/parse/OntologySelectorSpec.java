package edu.kit.ipd.are.agentanalysis.impl.specification.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import edu.kit.ipd.parse.ontologyselector.OntologySelector;

/**
 * Defines the agent specification for the {@link OntologySelector}.
 *
 * @author Dominik Fuchss
 *
 */
public class OntologySelectorSpec extends ParseAgentSpecification<OntologySelector> {
	/**
	 * Create the specification.
	 */
	public OntologySelectorSpec() {
		super(new OntologySelector());
		var props = ConfigManager.getConfiguration(OntologySelector.class);
		props.setProperty("ACTOR_ONTOLOGIES", this.loadOntologies("src/main/resources/ontology-selector/", //
				"robot.owl", "virtual_assistant.owl", "drone.owl", "lego_mindstorm.owl"));
		props.setProperty("ENVIRONMENT_ONTOLOGIES", this.loadOntologies("src/main/resources/ontology-selector/", //
				"kitchen.owl", "bedroom.owl", "bar.owl", "laundry.owl", "garden.owl", "childrens_room.owl", "heating.owl", "music.owl"));
		props.setProperty("OUTPUT", "target/");
	}

	private String loadOntologies(String base, String ontology, String... moreOntologies) {
		List<String> ontologies = new ArrayList<>(Arrays.asList(moreOntologies));
		ontologies.add(ontology);
		Collections.sort(ontologies);

		Iterator<String> iter = ontologies.iterator();

		String result = base + iter.next();
		while (iter.hasNext()) {
			result += "," + base + iter.next();
		}
		return result;
	}

	@Override
	public List<InformationId> getProvideIds() {
		return List.of(InformationId.ONTOLOGY);
	}

	@Override
	public List<InformationId> getRequiresIds() {
		return List.of(InformationId.TOPICS);
	}

}
