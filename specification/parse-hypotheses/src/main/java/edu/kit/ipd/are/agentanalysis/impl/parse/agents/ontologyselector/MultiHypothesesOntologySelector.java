package edu.kit.ipd.are.agentanalysis.impl.parse.agents.ontologyselector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.text.WordUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLNamedObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLEntityRenamer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.agents.MultiHypothesesTopicExtraction;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.BasicHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesManager;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesis;
import edu.kit.ipd.are.agentanalysis.port.util.Serialize;
import edu.kit.ipd.parse.luna.graph.INode;
import edu.kit.ipd.parse.luna.graph.INodeType;
import edu.kit.ipd.parse.luna.graph.Pair;
import edu.kit.ipd.parse.ontologyselector.OntologySelector;
import edu.kit.ipd.parse.ontologyselector.SelectionMethod;
import edu.kit.ipd.parse.ontologyselector.TopicOntology;
import edu.kit.ipd.parse.ontologyselector.merger.SimpleOntologyMerger;

/**
 * A {@link OntologySelector} with {@link IHypothesesManager}.
 *
 * @author Dominik Fuchss
 *
 */
public final class MultiHypothesesOntologySelector extends OntologySelector implements IHypothesesManager<PARSEGraphWrapper> {

	private static final String MULTI_HYPOTHESIS_ACTOR_ONTOLOGY_ATTRIBUTE = "MHOA-actor";
	private static final String MULTI_HYPOTHESIS_ENV_ONTOLOGY_ATTRIBUTE = "MHOA-env";

	private ObjectMapper objectMapper = Serialize.getObjectMapper(false);

	private static final TypeReference<List<OntologySelectionData>> ONTOLOGY_LIST = new TypeReference<>() {
	};

	private List<OntologySelectionData> actorOntos;
	private List<OntologySelectionData> envOntos;

	private final int maxHypothesis;

	/**
	 * Create the agent based on the max amount of hypotheses per hypotheses set.
	 *
	 * @param maxHypothesis the max amount of hypotheses per set
	 */
	public MultiHypothesesOntologySelector(int maxHypothesis) {
		this.maxHypothesis = Math.max(maxHypothesis, 1);
		this.setTopicExtraction(new MultiHypothesesTopicExtraction(maxHypothesis));
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.SENTENCE;
	}

	@Override
	protected void prepareGraph() {
		super.prepareGraph();
		INodeType topicType = this.graph.getNodeType(OntologySelector.ONTOLOGY_NODE_TYPE);
		if (!topicType.containsAttribute(MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ACTOR_ONTOLOGY_ATTRIBUTE, "String")) {
			topicType.addAttributeToType("String", MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ACTOR_ONTOLOGY_ATTRIBUTE);
		}
		if (!topicType.containsAttribute(MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ENV_ONTOLOGY_ATTRIBUTE, "String")) {
			topicType.addAttributeToType("String", MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ENV_ONTOLOGY_ATTRIBUTE);
		}
	}

	@Override
	protected List<TopicOntology> selectOntologies(SelectionMethod selectionMethod, Map<TopicOntology, Double> actorAgreements, Map<TopicOntology, Double> envAgreements) {
		// Store Ontologies before we select them ..
		List<TopicOntology> actorOnto = new ArrayList<>();
		for (Entry<TopicOntology, Double> ontology : actorAgreements.entrySet()) {
			actorOnto.add(ontology.getKey());
		}

		List<TopicOntology> envOnto = new ArrayList<>();
		for (Entry<TopicOntology, Double> ontology : envAgreements.entrySet()) {
			envOnto.add(ontology.getKey());
		}

		List<TopicOntology> selected = super.selectOntologies(selectionMethod, actorAgreements, envAgreements);

		this.actorOntos = new ArrayList<>();
		this.envOntos = new ArrayList<>();

		for (TopicOntology ontology : selected) {
			if (actorAgreements.containsKey(ontology)) {
				this.actorOntos.add(new OntologySelectionData(ontology, actorAgreements.get(ontology)));
			}
			if (envAgreements.containsKey(ontology)) {
				this.envOntos.add(new OntologySelectionData(ontology, envAgreements.get(ontology)));
			}
		}

		return selected;
	}

	@Override
	protected void annotateOntologyToGraph(OWLOntology ontology) {
		super.annotateOntologyToGraph(ontology);

		// Now store all ontologies ...
		List<INode> nodes = this.graph.getNodesOfType(this.graph.getNodeType(OntologySelector.ONTOLOGY_NODE_TYPE));
		if (nodes.size() != 1) {
			return;
		}
		INode ontologyNode = nodes.get(0);
		ontologyNode.setAttributeValue(MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ACTOR_ONTOLOGY_ATTRIBUTE, this.serializeOntologySelectionData(this.actorOntos));
		ontologyNode.setAttributeValue(MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ENV_ONTOLOGY_ATTRIBUTE, this.serializeOntologySelectionData(this.envOntos));
		this.actorOntos = null;
		this.envOntos = null;
	}

	@Override
	protected Pair<OWLOntology, String> mergeOntologies(List<TopicOntology> ontologies) {
		OntologySelector.logger.info("Skip Merge for MultiHypothesesOntologySelector ..");
		return new Pair<>(null, null);
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper graph) {
		List<INode> nodes = graph.getGraph().getNodesOfType(graph.getGraph().getNodeType(OntologySelector.ONTOLOGY_NODE_TYPE));
		if (nodes.size() != 1) {
			return new ArrayList<>();
		}
		INode ontologyNode = nodes.get(0);

		// Create Hypotheses.
		List<OntologySelectionData> actors = this.deserializeOntologySelectionData((String) ontologyNode.getAttributeValue(MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ACTOR_ONTOLOGY_ATTRIBUTE));
		actors.sort((t1, t2) -> -Double.compare(t1.getConfidence(), t2.getConfidence()));
		List<OntologySelectionData> envs = this.deserializeOntologySelectionData((String) ontologyNode.getAttributeValue(MultiHypothesesOntologySelector.MULTI_HYPOTHESIS_ENV_ONTOLOGY_ATTRIBUTE));
		envs.sort((t1, t2) -> -Double.compare(t1.getConfidence(), t2.getConfidence()));

		while (actors.size() > this.maxHypothesis && !actors.isEmpty()) {
			actors.remove(actors.size() - 1);
		}

		while (envs.size() > this.maxHypothesis && !envs.isEmpty()) {
			envs.remove(actors.size() - 1);
		}

		return Arrays.asList(new OntologyHypothesesSet(actors, OntologyType.ACTOR), new OntologyHypothesesSet(envs, OntologyType.ENVIRONMENT));
	}

	@Override
	public List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper graph) {
		INode node = graph.getGraph().getNodesOfType(graph.getGraph().getNodeType(OntologySelector.ONTOLOGY_NODE_TYPE)).get(0);
		@SuppressWarnings("unchecked")
		List<String> selected = (List<String>) node.getAttributeValue(OntologySelector.ONTOLOGY_SELECTED_ATTRIBUTE);
		List<OntologySelectionData> osds = new ArrayList<>();
		for (String onto : selected) {
			osds.add(new OntologySelectionData(onto, Double.NaN));
		}

		return Arrays.asList(new OntologyHypothesesSet(osds, null));
	}

	@Override
	public void applyHypothesesToDataStructure(PARSEGraphWrapper graph, List<IHypothesesSelection> hypotheses) {
		this.checkSelection(hypotheses);
		if (hypotheses.size() > 2) {
			throw new IllegalArgumentException("Invalid amount of HypothesesGroups are selected ..");
		}

		INodeType tokenType;
		if (graph.getGraph().hasNodeType(OntologySelector.ONTOLOGY_NODE_TYPE)) {
			tokenType = graph.getGraph().getNodeType(OntologySelector.ONTOLOGY_NODE_TYPE);
		} else {
			tokenType = graph.getGraph().createNodeType(OntologySelector.ONTOLOGY_NODE_TYPE);
		}
		if (!tokenType.containsAttribute(OntologySelector.ONTOLOGY_ATTRIBUTE, "org.semanticweb.owlapi.model.OWLOntology")) {
			tokenType.addAttributeToType("org.semanticweb.owlapi.model.OWLOntology", OntologySelector.ONTOLOGY_ATTRIBUTE);
		}

		List<OntologySelectionData> allData = new ArrayList<>();
		this.loadOntologies(allData, hypotheses);

		// Copied from exec ..
		OWLOntologyManager owlManager = OWLManager.createOWLOntologyManager();
		String mergeId = this.getMergedOntoIdentificator(allData);

		OWLOntology merged = this.mergeOntologies(owlManager, allData, mergeId);
		String filename = this.outputFolder + "merged_" + mergeId + ".owl";
		File file = new File(filename);
		this.saveOntologyToFile(owlManager, merged, file);

		// From Annotate to Graph:
		INodeType type = graph.getGraph().getNodeType(OntologySelector.ONTOLOGY_NODE_TYPE);
		INode node = graph.getGraph().getNodesOfType(type).isEmpty() ? graph.getGraph().createNode(type) : graph.getGraph().getNodesOfType(type).get(0);
		node.setAttributeValue(OntologySelector.ONTOLOGY_ATTRIBUTE, merged);
	}

	private String getMergedOntoIdentificator(List<OntologySelectionData> ontologies) {
		// Copied from base class.
		List<String> names = new ArrayList<>();
		for (OntologySelectionData to : ontologies) {
			String desc = to.getDescription();
			desc = desc.replace("http://www.semanticweb.org/", "").replace("environment_", "").replace("_", "").replace("actor_", "");
			names.add(WordUtils.capitalize(desc));
		}
		return names.stream().sorted().collect(Collectors.joining());
	}

	private OWLOntology mergeOntologies(OWLOntologyManager owlManager, List<OntologySelectionData> ontologies, String mergeId) {
		// Copied from base class.
		OWLOntology merged = this.mergeSelection(owlManager, ontologies, IRI.create("http://www.semanticweb.com/mergedOntology_" + mergeId));
		return merged;
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

	private List<OntologySelectionData> deserializeOntologySelectionData(String data) {
		try {
			List<OntologySelectionData> ontologies = this.objectMapper.readValue(data, MultiHypothesesOntologySelector.ONTOLOGY_LIST);
			return ontologies;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return List.of();
		}
	}

	private String serializeOntologySelectionData(List<OntologySelectionData> ontologies) {
		try {
			String data = this.objectMapper.writeValueAsString(ontologies);
			return data;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
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

	//////////////////
	///// MERGE //////
	//////////////////

	private OWLOntology mergeSelection(OWLOntologyManager owlManager, List<OntologySelectionData> ontologies, IRI iri) {
		// Copied from #merge in ontology merger..

		Set<OWLAxiom> axioms = new HashSet<>();
		Set<OWLImportsDeclaration> imports = new HashSet<>();
		OWLOntology merged = null;

		try {
			merged = owlManager.createOntology(iri);
			for (OntologySelectionData ontology : ontologies) {
				IRI tIRI = IRI.create(new File(ontology.getOntologyPath()).toURI());
				OWLOntology onto = owlManager.loadOntologyFromOntologyDocument(tIRI);
				onto.axioms().forEach(axioms::add);
				onto.importsDeclarations().forEach(imports::add);
				owlManager.removeOntology(onto);
			}

			owlManager.addAxioms(merged, axioms.stream());
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}

		// Adding the import declarations
		for (OWLImportsDeclaration decl : imports) {
			owlManager.applyChange(new AddImport(merged, decl));
		}

		SimpleOntologyMerger som = new SimpleOntologyMerger();

		// rename everything to use the merged ontology's IRI
		this.renameAllSelections(owlManager, ontologies, iri);
		// add the properties to connect dataTypes to Objects
		som.connectTypesToObjects(owlManager, merged, iri);
		return merged;

	}

	private void renameAllSelections(OWLOntologyManager owlManager, List<OntologySelectionData> ontologies, IRI iri) {
		// Copied from #renameAll in ontology merger..
		OWLEntityRenamer renamer = new OWLEntityRenamer(owlManager, owlManager.ontologies().collect(Collectors.toList()));
		for (OntologySelectionData selection : ontologies) {
			OWLOntology ontology = this.loadOntology(selection.getOntologyPath());
			if (ontology != null) {
				ontology.axioms().flatMap(OWLAxiom::components).filter(c -> c instanceof OWLNamedObject).map(c -> (OWLNamedObject) c)
						.filter(no -> !no.getIRI().toString().contains("w3.org") && !no.getIRI().toString().contains("Thing")).forEach(obj -> {
							IRI individualName = IRI.create(obj.getIRI().toString().replaceFirst("[^*]+(?=#|;)", iri.toString()));
							owlManager.applyChanges(renamer.changeIRI(obj.getIRI(), individualName));
						});
			}
		}
	}

	private OWLOntology loadOntology(String ontologyPath) {
		OWLOntology onto = null;
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		try {
			onto = manager.loadOntologyFromOntologyDocument(new FileInputStream(ontologyPath));
		} catch (OWLOntologyCreationException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return onto;
	}

}
