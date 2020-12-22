package edu.kit.ipd.eagle.impl.specification.parse.hypothesis;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;

final class OntologySelectionData implements IHypothesis {

	private static final long serialVersionUID = -4230368295523221012L;

	private String ontologyPath;
	private String description;
	private double score;

	OntologySelectionData(String description, String path, double score) {
		this.ontologyPath = path;
		this.description = description;
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

	@Override
	public String getValue() {
		return this.description;
	}

	@JsonIgnore
	String getDescription() {
		return this.description;
	}

	@JsonIgnore
	String getOntologyPath() {
		return this.ontologyPath;
	}

	@Override
	public String toString() {
		return "OntologySelectionData [ontologyPath=" + this.ontologyPath + ", description=" + this.description + ", score=" + this.score + "]";
	}

}