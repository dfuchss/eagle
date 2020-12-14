package edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgent;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.parse.ontologyselector.OntologySelector;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void applyHypothesesToDataStructure(PARSEGraphWrapper data, List<IHypothesesSelection> hypotheses) {
		// TODO Auto-generated method stub

	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.SENTENCE;
	}
}
