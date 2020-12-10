package edu.kit.ipd.are.agentanalysis.impl.parse.specification.hypothesis;

import java.util.List;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgent;
import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.AbstractAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.InformationId;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesManager;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

public abstract class AbstractAgentHypothesisSpecification<A extends AbstractAgent & IHypothesesManager<PARSEGraphWrapper>> implements IAgentHypothesisSpecification<PARSEAgent, PARSEGraphWrapper> {

	public static final int DEFAULT_HYPOTHESES = 3;

	protected final A agentInstance;
	private AbstractAgentSpecification<? super A> agentSpec;

	protected AbstractAgentHypothesisSpecification(AbstractAgentSpecification<? super A> agentSpec, A agent) {
		this.agentInstance = agent;
		this.agentSpec = agentSpec;
		this.agentSpec.setAgentInstance(this.agentInstance);
	}

	@Override
	public final PARSEAgent getAgentInstance() {
		return new PARSEAgent(this.agentInstance);
	}

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
	public final List<IHypothesesSet> getHypothesesFromGraph(PARSEGraphWrapper graph) {
		return this.agentInstance.getHypothesesFromGraph(graph);
	}

	@Override
	public final void applyHypothesesToGraph(PARSEGraphWrapper graph, List<IHypothesesSelection> selection) {
		this.agentInstance.applyHypothesesToGraph(graph, selection);
	}

	@Override
	public final List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper graph) {
		return this.agentInstance.getHypothesesForNonHypothesesExecution(graph);
	}
}
