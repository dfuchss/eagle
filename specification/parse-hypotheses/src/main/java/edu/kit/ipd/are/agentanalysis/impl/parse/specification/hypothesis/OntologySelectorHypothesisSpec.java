package edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis;

import edu.kit.ipd.are.agentanalysis.impl.parse.agents.ontologyselector.MultiHypothesesOntologySelector;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;

/**
 * Defines the agent specification for the
 * {@link MultiHypothesesOntologySelector}. This is the hypotheses realization
 * for {@link OntologySelectorSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class OntologySelectorHypothesisSpec extends AbstractAgentHypothesisSpecification<MultiHypothesesOntologySelector> {
	/**
	 * Create the specification by using the default amount of hypotheses.
	 *
	 * @param actorOntologies the actor ontologies see
	 *                        {@link OntologySelectorSpec#loadOntologies(String, String, String...)}
	 * @param envOntologies   the environment ontologies see
	 *                        {@link OntologySelectorSpec#loadOntologies(String, String, String...)}
	 */
	public OntologySelectorHypothesisSpec(String actorOntologies, String envOntologies) {
		this(AbstractAgentHypothesisSpecification.DEFAULT_HYPOTHESES, actorOntologies, envOntologies);
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
		super(new OntologySelectorSpec(actorOntologies, envOntologies), new MultiHypothesesOntologySelector(maxHypotheses));
	}
}
