package edu.kit.ipd.are.agentanalysis.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.ipd.are.agentanalysis.impl.execution.AgentHelper;
import edu.kit.ipd.are.agentanalysis.impl.prepipeline.IndirectPrePipeline;
import edu.kit.ipd.are.agentanalysis.impl.prepipeline.PARSEPrePipeline;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.TopicExtractionSpec;
import edu.kit.ipd.are.agentanalysis.impl.specification.parse.WikiWSDSpec;
import edu.kit.ipd.are.agentanalysis.port.AgentAnalysisConfiguration;
import edu.kit.ipd.are.agentanalysis.port.IAgentSpecification;
import edu.kit.ipd.are.agentanalysis.port.PrePipelineMode;

/**
 * Some tests for {@link AgentHelper}.
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
		List<IAgentSpecification<?>> agents = Arrays.asList(new TopicExtractionSpec(), new WikiWSDSpec(), new OntologySelectorSpec());

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
		List<IAgentSpecification<?>> agents = Arrays.asList(new WikiWSDSpec(), new OntologySelectorSpec());
		var ordered = AgentHelper.findAgentOrder(agents);
		Assert.assertNull(ordered);
	}

	/**
	 * Check functionality of
	 * {@link AgentHelper#findInvalidAgents(java.util.Collection, org.fuchss.agentanalysis.port.PrePipelineMode)}
	 * and {@link AgentAnalysisConfiguration#isOverridePrePiplineRestricitions()}
	 */
	@Test
	public void checkInvalidAgentCheck() {
		IAgentSpecification<?> someParseAgent = new WikiWSDSpec();
		Assert.assertEquals(PrePipelineMode.PARSE, someParseAgent.getMode());

		var parsePrePipeline = new PARSEPrePipeline();
		Assert.assertEquals(PrePipelineMode.PARSE, parsePrePipeline.getMode());

		Assert.assertTrue(AgentHelper.findInvalidAgents(List.of(someParseAgent), PrePipelineMode.PARSE).isEmpty());

		var indirectPrePipeline = new IndirectPrePipeline();
		Assert.assertEquals(PrePipelineMode.INDIRECT, indirectPrePipeline.getMode());
		Assert.assertEquals(1, AgentHelper.findInvalidAgents(List.of(someParseAgent), PrePipelineMode.INDIRECT).size());

		// Check whether override works ..
		AgentAnalysisConfiguration.setOverridePrePipelineRestrictions(true);
		Assert.assertTrue(AgentHelper.findInvalidAgents(List.of(someParseAgent), PrePipelineMode.PARSE).isEmpty());
		Assert.assertTrue(AgentHelper.findInvalidAgents(List.of(someParseAgent), PrePipelineMode.INDIRECT).isEmpty());

	}
}
