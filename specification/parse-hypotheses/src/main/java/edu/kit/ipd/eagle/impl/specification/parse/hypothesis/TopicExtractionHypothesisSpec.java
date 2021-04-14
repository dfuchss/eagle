package edu.kit.ipd.eagle.impl.specification.parse.hypothesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import edu.kit.ipd.eagle.impl.platforms.parse.PARSEAgent;
import edu.kit.ipd.eagle.impl.platforms.parse.PARSEGraphWrapper;
import edu.kit.ipd.eagle.impl.specification.parse.TopicExtractionSpec;
import edu.kit.ipd.eagle.port.hypothesis.BasicHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.HypothesisRange;
import edu.kit.ipd.eagle.port.hypothesis.IAgentHypothesisSpecification;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesis;
import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.INode;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import edu.kit.ipd.pronat.topic_extraction.TopicExtraction;
import edu.kit.ipd.pronat.topic_extraction_common.Topic;
import edu.kit.ipd.pronat.topic_extraction_common.TopicExtractionCore;

/**
 * Defines the agent specification for the {@link TopicExtraction}. This is the hypotheses realization for
 * {@link TopicExtractionSpec}.
 *
 * @author Dominik Fuchss
 *
 */
public class TopicExtractionHypothesisSpec extends TopicExtractionSpec implements IAgentHypothesisSpecification<PARSEAgent, PARSEGraphWrapper> {

	private static final String TOPICS_NODE_TYPE = "topics";

	/**
	 * Create the specification by using the default amount of hypotheses.
	 */
	public TopicExtractionHypothesisSpec() {
		this(IAgentHypothesisSpecification.DEFAULT_HYPOTHESES);
	}

	/**
	 * Create the specification by using a specific amount of hypotheses.
	 *
	 * @param maxHypotheses the specific maximum of generated hypotheses per {@link IHypothesesSet}
	 */
	public TopicExtractionHypothesisSpec(int maxHypotheses) {
		// Set Max Hypotheses in Agent's configuration.
		Properties props = ConfigManager.getConfiguration(TopicExtraction.class);
		props.setProperty("TOPICS", String.valueOf(Math.max(maxHypotheses, -1)));
	}

	@Override
	public List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper data) {
		IGraph graph = data.getGraph();
		List<Topic> topics = TopicExtractionCore.getTopicsFromIGraph(graph);

		List<TopicHypothesis> hyps = new ArrayList<>();
		for (Topic t : topics) {
			hyps.add(new TopicHypothesis(t));
		}
		return Arrays.asList(new BasicHypothesesSet("Topic Hypotheses", HypothesisRange.INPUT, hyps, false));
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromDataStructure(PARSEGraphWrapper data) {
		IGraph graph = data.getGraph();
		List<INode> nodes = graph.getNodesOfType(graph.getNodeType(TopicExtractionHypothesisSpec.TOPICS_NODE_TYPE));
		if (nodes.size() != 1) {
			return new ArrayList<>();
		}

		List<Topic> topics = TopicExtractionCore.getTopicsFromIGraph(graph);
		topics.sort((t1, t2) -> -Double.compare(t1.getScore(), t2.getScore()));

		List<TopicHypothesis> hyps = new ArrayList<>();
		for (Topic t : topics) {
			hyps.add(new TopicHypothesis(t));
		}
		if (hyps.isEmpty()) {
			return List.of();
		}
		return Arrays.asList(new BasicHypothesesSet("Topic Hypotheses", HypothesisRange.INPUT, hyps, false));
	}

	@Override
	public void applyHypothesesToDataStructure(PARSEGraphWrapper data, List<IHypothesesSelection> hypotheses) {
		IGraph graph = data.getGraph();
		this.checkSelection(hypotheses);
		if (hypotheses.size() > 1) {
			throw new IllegalArgumentException("Too many HypothesesGroups are selected ..");
		}

		List<IHypothesis> hyps = hypotheses.get(0).getSelectedHypotheses();
		List<Topic> topics = hyps.stream().map(h -> ((TopicHypothesis) h).topic).collect(Collectors.toList());
		TopicExtractionCore.setTopicsToInputGraph(topics, graph);
	}

	private void checkSelection(List<IHypothesesSelection> selection) {
		for (IHypothesesSelection s : selection) {
			if (!(s.getAllHypotheses() instanceof BasicHypothesesSet)) {
				throw new IllegalArgumentException("Not a valid HypothesesGroup: " + s.getAllHypotheses());
			}
			for (IHypothesis h : s.getSelectedHypotheses()) {
				if (!(h instanceof TopicHypothesis)) {
					throw new IllegalArgumentException("Not a valid TopicHypothesis: " + h);
				}
			}
		}
	}

	private static final class TopicHypothesis implements IHypothesis {

		private static final long serialVersionUID = -6704832506392171553L;

		private Topic topic;

		// For deserialize
		private TopicHypothesis() {
		}

		private TopicHypothesis(Topic topic) {
			this.topic = topic;
		}

		@Override
		public String getPrettyInformation() {
			return this.topic.getLabel();
		}

		@Override
		public double getConfidence() {
			return this.topic.getScore();
		}

		@Override
		public String toString() {
			return "Hypothesis [topic=" + this.topic + ", score=" + this.topic.getScore() + "]";
		}

		@Override
		public String getValue() {
			return this.topic.getLabel();
		}
	}

	@Override
	public HypothesisRange getHypothesesRange() {
		return HypothesisRange.INPUT;
	}
}
