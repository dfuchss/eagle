package edu.kit.ipd.are.agentanalysis.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEAgentHelper;
import edu.kit.ipd.are.agentanalysis.impl.parse.prepipeline.IndirectPrePipeline;
import edu.kit.ipd.are.agentanalysis.impl.parse.prepipeline.PARSEPrePipeline;
import edu.kit.ipd.are.agentanalysis.impl.parse.specification.parse.ParseAgentSpecification;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.TopicExtractionSpec;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.WikiWSDSpec;
import edu.kit.ipd.are.agentanalysis.port.AgentHelper;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;
import edu.kit.ipd.parse.luna.agent.AbstractAgent;

/**
 * Some tests for {@link PARSEAgentHelper} and {@link AgentHelper}.
 *
 * @author Dominik Fuchss
 *
 */
public class AgentHelperTest extends TestBase {

	/**
	 * Check {@link AgentHelper#findAgentOrder(java.util.Collection)} with a valid
	 * set of agents.
	 */
	@Test
	public void testFindAgentOrderValid() {
		List<ParseAgentSpecification<? extends AbstractAgent>> agents = Arrays.asList(new TopicExtractionSpec(), new WikiWSDSpec(), new OntologySelectorSpec());

		var ordered = AgentHelper.findAgentOrder(agents);
		Assert.assertNotNull(ordered);
		ordered.forEach(a -> Assert.assertTrue(agents.contains(a)));

		Assert.assertTrue(ordered.get(0) instanceof WikiWSDSpec);
		Assert.assertTrue(ordered.get(1) instanceof TopicExtractionSpec);
		Assert.assertTrue(ordered.get(2) instanceof OntologySelectorSpec);

	}

	/**
	 * Check {@link AgentHelper#findAgentOrder(java.util.Collection)} with a invalid
	 * set of agents
	 */
	@Test
	public void testFindAgentOrderInValid() {
		List<ParseAgentSpecification<? extends AbstractAgent>> agents = Arrays.asList(new WikiWSDSpec(), new OntologySelectorSpec());
		var ordered = AgentHelper.findAgentOrder(agents);
		Assert.assertNull(ordered);
	}

	/**
	 * Check functionality of
	 * {@link AgentHelper#findInvalidAgents(java.util.Collection, org.fuchss.agentanalysis.port.PrePipelineMode)}.
	 */
	@Test
	public void checkInvalidAgentCheck() {
		List<ParseAgentSpecification<? extends AbstractAgent>> someParseAgent = List.of(new WikiWSDSpec());
		Assert.assertEquals(PrePipelineMode.PARSE, someParseAgent.get(0).getMode());

		var parsePrePipeline = new PARSEPrePipeline();
		Assert.assertEquals(PrePipelineMode.PARSE, parsePrePipeline.getMode());

		Assert.assertTrue(PARSEAgentHelper.findInvalidAgents(someParseAgent, PrePipelineMode.PARSE).isEmpty());

		var indirectPrePipeline = new IndirectPrePipeline();
		Assert.assertEquals(PrePipelineMode.INDIRECT, indirectPrePipeline.getMode());
		Assert.assertEquals(1, PARSEAgentHelper.findInvalidAgents(someParseAgent, PrePipelineMode.INDIRECT).size());

	}
}
