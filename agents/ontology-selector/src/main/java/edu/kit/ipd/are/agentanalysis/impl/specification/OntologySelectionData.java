package edu.kit.ipd.are.agentanalysis.impl.specification;

import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesis;
import edu.kit.ipd.parse.ontologyselector.TopicOntology;

public class OntologySelectionData implements IHypothesis {

	private static final long serialVersionUID = -4230368295523221012L;

	private String ontologyPath;
	private String description;
	private double score;

	/**
	 * For JSON Serializer
	 *
	 * @author Dominik Fuchss
	 */
	public OntologySelectionData() {
	}

	public OntologySelectionData(String description, double score) {
		this.ontologyPath = null;
		this.description = description;
		this.score = score;
	}

	public OntologySelectionData(TopicOntology ontology, double score) {
		this.ontologyPath = ontology.getOntologyPath();
		this.description = ontology.getDescription();
		this.score = score;
	}

	@Override
	public String getPrettyInformation() {
		return this.description;
	}

	@Override
	public double getConfidence() {
		return this.score;
	}

	public String getDescription() {
		return this.description;
	}

	public String getOntologyPath() {
		return this.ontologyPath;
	}

	@Override
	public String getValue() {
		return this.description;
	}

	@Override
	public String toString() {
		return "OntologySelectionData [ontologyPath=" + this.ontologyPath + ", description=" + this.description + ", score=" + this.score + "]";
	}

}