package edu.kit.ipd.eagle.impl.specification.parse.hypothesis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.INode;
import edu.kit.ipd.parse.luna.graph.INodeType;
import edu.kit.ipd.parse.luna.graph.Pair;
import edu.kit.ipd.parse.ontology_selector.OntologySelector;

/**
 * Helper methods copied (and adopted) from {@link OntologySelector}.
 *
 * @author Dominik Fuchss
 *
 */
final class OntologySelectorHelper {
	private static final String ONTOLOGY_NODE_TYPE = "ontology";

	private static final String ONTOLOGY_PATH_ATTRIBUTE = "ontologyPath";
	private static final String SELECTED_ONTOLOGIES_ATTRIBUTE = "selectedOntologies";
	private static final String ONTOLOGY_AGREEMENTS_ATTRIBUTE = "ontologyAgreements";

	private OntologySelectorHelper() {
		throw new IllegalAccessError();
	}

	public static void annotateOntologyToGraph(IGraph graph, String path) {
		final INode node = OntologySelectorHelper.getOrCreateOntologyNode(graph);
		node.setAttributeValue(OntologySelectorHelper.ONTOLOGY_PATH_ATTRIBUTE, path);
	}

	public static void annotateAgreementsToGraph(IGraph graph, List<OntologySelectionData> allAgreements) {
		final INode node = OntologySelectorHelper.getOrCreateOntologyNode(graph);
		final List<Pair<String, Double>> agreements = new ArrayList<>();
		for (OntologySelectionData entry : allAgreements) {
			final String id = entry.getDescription();
			final Double agreement = entry.getConfidence();
			agreements.add(new Pair<>(id, agreement));
		}
		node.setAttributeValue(OntologySelectorHelper.ONTOLOGY_AGREEMENTS_ATTRIBUTE, Collections.unmodifiableList(agreements));
	}

	public static void annotateSelectedOntologiesToGraph(IGraph graph, List<OntologySelectionData> ontologies) {
		final INode node = OntologySelectorHelper.getOrCreateOntologyNode(graph);
		final List<Pair<String, String>> annotationPairs = new ArrayList<>();
		for (final OntologySelectionData ontology : ontologies) {
			final String id = ontology.getDescription();
			final String path = ontology.getOntologyPath();
			annotationPairs.add(new Pair<>(id, path));
		}
		node.setAttributeValue(OntologySelectorHelper.SELECTED_ONTOLOGIES_ATTRIBUTE, Collections.unmodifiableList(annotationPairs));
	}

	private static INode getOrCreateOntologyNode(IGraph graph) {
		final INodeType nodeType = graph.getNodeType(OntologySelectorHelper.ONTOLOGY_NODE_TYPE);
		final List<INode> ontologyNodes = graph.getNodesOfType(nodeType);
		if (ontologyNodes.isEmpty()) {
			return graph.createNode(nodeType);
		} else if (ontologyNodes.size() > 1) {
			for (int i = 1; i < ontologyNodes.size(); i++) {
				graph.deleteNode(ontologyNodes.get(i));
			}
		}
		return ontologyNodes.get(0);
	}

	public static Optional<OWLOntology> loadOntology(String path) {
		OWLOntology onto = null;
		final OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

		try {
			onto = manager.loadOntologyFromOntologyDocument(new FileInputStream(path));
		} catch (OWLOntologyCreationException | FileNotFoundException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(onto);
	}

	public static String checkPathAndAttemptFixing(final String ontologyPath) {
		String path = ontologyPath;
		// check if the path is correct the way it is provided by checking existence
		if (!new File(path).exists()) {
			// not existing, then try with getting as resource
			final URL pathURL = OntologySelector.class.getResource(path);
			path = null;
			if (pathURL != null) {
				path = pathURL.getFile();
				if (!new File(path).exists()) {
					// still not found, return null because path could not be resolved
					return null;
				}
			}
		}
		return path;
	}
}
