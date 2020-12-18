package edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgent;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.BasicHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesis;
import edu.kit.ipd.parse.luna.graph.Pair;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import edu.kit.ipd.parse.ontology_selector.OntologySelector;

/**
 * Defines the agent specification for the {@link OntologySelector}. This is the
 * hypotheses realization for {@link OntologySelectorSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class OntologySelectorHypothesisSpec extends OntologySelectorSpec implements IAgentHypothesisSpecification<PARSEAgent, PARSEGraphWrapper> {
	private int maxHypotheses;

	/**
	 * Create the specification by using the default amount of hypotheses.
	 *
	 * @param actorOntologies the actor ontologies see
	 *                        {@link OntologySelectorSpec#loadOntologies(String, String, String...)}
	 * @param envOntologies   the environment ontologies see
	 *                        {@link OntologySelectorSpec#loadOntologies(String, String, String...)}
	 */
	public OntologySelectorHypothesisSpec(String actorOntologies, String envOntologies) {
		this(IAgentHypothesisSpecification.DEFAULT_HYPOTHESES, actorOntologies, envOntologies);
	}

	/**
	 * Create the specification by using a specific amount of hypotheses.
	 *
	 * @param maxHypotheses   the specific maximum of generated hypotheses per
	 *                        {@link IHypothesesSet}
	 * @param actorOntologies the actor ontologies see
	 *                        {@link OntologySelectorSpec#loadOntologies(String, String, String...)}
	 * @param envOntologies   the environment ontologies see
	 *                        {@link OntologySelectorSpec#loadOntologies(String, String, String...)}
	 */
	public OntologySelectorHypothesisSpec(int maxHypotheses, String actorOntologies, String envOntologies) {
		super(actorOntologies, envOntologies);
		this.maxHypotheses = maxHypotheses;
	}

	@Override
	public List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper data) {
		List<Pair<String, String>> selected = OntologySelector.getSelectedOntologiesFromIGraph(data.getGraph());
		List<OntologySelectionData> osds = new ArrayList<>();
		for (Pair<String, String> idXPath : selected) {
			osds.add(new OntologySelectionData(idXPath.getLeft(), idXPath.getRight(), Double.NaN));
		}

		return Arrays.asList(new OntologyHypothesesSet(osds, null));
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper data) {
		List<Pair<String, Double>> idXConfidence = OntologySelector.getAgreementsFromIGraph(data.getGraph());
		List<Pair<String, String>> selectedIdXPath = OntologySelector.getSelectedOntologiesFromIGraph(data.getGraph());

		var props = ConfigManager.getConfiguration(OntologySelector.class);
		String actors = props.getProperty("ACTOR_ONTOLOGIES", "");
		String envs = props.getProperty("ENVIRONMENT_ONTOLOGIES", "");
		actors = this.toOntologyIds(actors);
		envs = this.toOntologyIds(envs);

		List<OntologySelectionData> osdActor = new ArrayList<>();
		List<OntologySelectionData> osdEnv = new ArrayList<>();
		for (var selected : selectedIdXPath) {
			String id = selected.getLeft();
			double confidence = idXConfidence.stream().filter(p -> p.getLeft().equals(id)).map(Pair::getRight).findFirst().orElse(Double.NaN);
			boolean isActor = actors.contains(id);
			boolean isEnv = envs.contains(id);

			if (isActor == isEnv) {
				throw new IllegalStateException("Ontology is (or is not) Actor and Environment: " + id);
			}

			if (isActor) {
				osdActor.add(new OntologySelectionData(id, selected.getRight(), confidence));
			}
			if (isEnv) {
				osdEnv.add(new OntologySelectionData(id, selected.getRight(), confidence));
			}
		}

		osdActor.sort((t1, t2) -> -Double.compare(t1.getConfidence(), t2.getConfidence()));
		osdEnv.sort((t1, t2) -> -Double.compare(t1.getConfidence(), t2.getConfidence()));

		while (osdActor.size() > this.maxHypotheses && !actors.isEmpty()) {
			osdActor.remove(osdActor.size() - 1);
		}

		while (osdEnv.size() > this.maxHypotheses && !envs.isEmpty()) {
			osdEnv.remove(osdEnv.size() - 1);
		}

		return Arrays.asList(new OntologyHypothesesSet(osdActor, OntologyType.ACTOR), new OntologyHypothesesSet(osdEnv, OntologyType.ENVIRONMENT));
	}

	private String toOntologyIds(String ontologyString) {
		String result = "";
		for (final String ontologyPath : ontologyString.split(",")) {
			String path = OntologySelectorHelper.checkPathAndAttemptFixing(ontologyPath);
			OWLOntology onto = OntologySelectorHelper.loadOntology(path).orElse(null);
			if (onto != null) {
				String id;
				final Optional<IRI> iri = onto.getOntologyID().getOntologyIRI();
				if (iri.isPresent()) {
					id = iri.get().toString();
				} else {
					id = onto.getOntologyID().toString();
				}
				result += id + "|";
			}
		}
		return result.isBlank() ? result : result.substring(0, result.length() - 1);
	}

	@Override
	public void applyHypothesesToDataStructure(PARSEGraphWrapper data, List<IHypothesesSelection> hypotheses) {
		this.checkSelection(hypotheses);
		if (hypotheses.size() > 2) {
			throw new IllegalArgumentException("Invalid amount of HypothesesGroups are selected ..");
		}

		List<OntologySelectionData> allData = new ArrayList<>();
		this.loadOntologies(allData, hypotheses);

		OntologySelectorHelper.annotateAgreementsToGraph(data.getGraph(), allData);
		OntologySelectorHelper.annotateSelectedOntologiesToGraph(data.getGraph(), allData);

		// TODO Save merged ontology
		this.logger.warn("Currently merged ontology is not saved ..");

	}

	private void loadOntologies(List<OntologySelectionData> allData, List<IHypothesesSelection> hypotheses) {
		boolean[] alreadySelected = new boolean[OntologyType.values().length];

		for (IHypothesesSelection s : hypotheses) {
			OntologyType type = ((OntologyHypothesesSet) s.getAllHypotheses()).type;
			if (alreadySelected[type.ordinal()]) {
				throw new IllegalArgumentException("OntologyType is present multiple times in HypothesesSelection");
			} else {
				alreadySelected[type.ordinal()] = true;
			}

			List<OntologySelectionData> datas = ((OntologyHypothesesSet) s).getHypotheses().stream().map(h -> (OntologySelectionData) h).collect(Collectors.toList());
			allData.addAll(datas);
		}

	}

	private void checkSelection(List<IHypothesesSelection> selection) {
		for (IHypothesesSelection s : selection) {
			if (!(s.getAllHypotheses() instanceof OntologyHypothesesSet)) {
				throw new IllegalArgumentException("Not a valid HypothesesGroup: " + s.getAllHypotheses());
			}
			OntologyHypothesesSet g = (OntologyHypothesesSet) s.getAllHypotheses();
			List<IHypothesis> all = g.getHypotheses();
			for (IHypothesis h : s.getSelectedHypotheses()) {
				if (!all.contains(h) || !(h instanceof OntologySelectionData)) {
					throw new IllegalArgumentException("Not a valid OntologySelectionData: " + h);
				}
			}
		}
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.SENTENCE;
	}

	private static class OntologyHypothesesSet extends BasicHypothesesSet {
		private static final long serialVersionUID = 7066939699813664494L;

		private OntologyType type;

		// For deserialize
		private OntologyHypothesesSet() {
			super(null, HypothesisRange.SENTENCE, null, false);
		}

		OntologyHypothesesSet(List<OntologySelectionData> ontologies, OntologyType type) {
			super("OntologyType: " + type, HypothesisRange.SENTENCE, ontologies, false);
			this.type = type;
		}
	}

	private enum OntologyType {
		ACTOR, ENVIRONMENT
	}
}
