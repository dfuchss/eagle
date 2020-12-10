package edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgent;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.prepipeline.PrePipelineMode;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.AbstractAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesManager;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

/**
 * The base class for the agent hypothesis specification in the PARSE and
 * INDIRECT context.
 *
 * @author Dominik Fuchss
 *
 * @param <A> the new type of agent (with hypotheses support)
 */
public abstract class AbstractAgentHypothesisSpecification<A extends AbstractAgent & IHypothesesManager<PARSEGraphWrapper>> extends AbstractAgentSpecification<A>
		implements IAgentHypothesisSpecification<PARSEAgent, PARSEGraphWrapper> {

	/**
	 * Just some default number for the amount of hypotheses to be generated.
	 */
	public static final int DEFAULT_HYPOTHESES = 3;

	private AbstractAgentSpecification<? super A> agentSpec;

	/**
	 * Create a agent hypothesis specification by using the original agent
	 * specification and the new agent (that is also a {@link IHypothesesManager}).
	 *
	 * @param agentSpec the old agent specification without hypotheses support
	 * @param agent     the new agent with hypotheses support
	 */
	protected AbstractAgentHypothesisSpecification(AbstractAgentSpecification<? super A> agentSpec, A agent) {
		super(agent);
		this.agentSpec = agentSpec;
		this.agentSpec.setAgentInstance(this.agentInstance);
	}

	/**
	 * Get the necessary type of PrePipeline.
	 *
	 * @return the type of prepipeline needed
	 */
	@Override
	public final PrePipelineMode getMode() {
		return this.agentSpec.getMode();
	}

	@Override
	public final List<InformationId> getProvideIds() {
		return this.agentSpec.getProvideIds();
	}

	@Override
	public final List<InformationId> getRequiresIds() {
		return this.agentSpec.getRequiresIds();
	}

	@Override
	public final List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper graph) {
		return this.agentInstance.getHypothesesFromDataStructure(graph);
	}

	@Override
	public final void applyHypothesesToDataStructure(PARSEGraphWrapper graph, List<IHypothesesSelection> selection) {
		this.agentInstance.applyHypothesesToDataStructure(graph, selection);
	}

	@Override
	public final List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper graph) {
		return this.agentInstance.getHypothesesForNonHypothesesExecution(graph);
	}
}
