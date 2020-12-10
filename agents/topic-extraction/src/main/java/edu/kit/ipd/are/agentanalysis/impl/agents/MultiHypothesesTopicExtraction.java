package edu.kit.ipd.are.agentanalysis.impl.agents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kit.ipd.are.agentanalysis.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.BasicHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.HypothesisRange;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesManager;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.are.agentanalysis.port.hypothesis.IHypothesis;
import edu.kit.ipd.are.agentanalysis.port.util.Serialize;
import edu.kit.ipd.parse.luna.graph.IGraph;
import edu.kit.ipd.parse.luna.graph.INode;
import edu.kit.ipd.parse.luna.graph.INodeType;
import edu.kit.ipd.parse.topicextraction.Topic;
import edu.kit.ipd.parse.topicextraction.TopicExtraction;

/**
 * A implementation of {@link TopicExtraction} with {@link IHypothesesManager}.
 *
 * @author Dominik Fuchss
 *
 */
public class MultiHypothesesTopicExtraction extends TopicExtraction implements IHypothesesManager<PARSEGraphWrapper> {
	public static final String MULTI_HYPOTHESIS_TOPIC_ATTRIBUTE = "MHTA-topic";

	private ObjectMapper objectMapper = Serialize.getObjectMapper(false);
	private static final TypeReference<List<Topic>> TOPIC_LIST = new TypeReference<List<Topic>>() {
	};

	private final int maxHypothesis;

	public MultiHypothesesTopicExtraction(int maxHypothesis) {
		this.maxHypothesis = Math.max(maxHypothesis, 1);
	}

	@Override
	protected void prepareGraph() {
		super.prepareGraph();
		INodeType topicType = this.graph.getNodeType(TopicExtraction.TOPICS_NODE_TYPE);
		if (!topicType.containsAttribute(MULTI_HYPOTHESIS_TOPIC_ATTRIBUTE, "String")) {
			topicType.addAttributeToType("String", MULTI_HYPOTHESIS_TOPIC_ATTRIBUTE);
		}
	}

	@Override
	protected void addTopicsToInputGraph(List<Topic> topics) {
		super.addTopicsToInputGraph(topics);

		List<INode> nodes = this.graph.getNodesOfType(this.graph.getNodeType(TopicExtraction.TOPICS_NODE_TYPE));
		if (nodes.size() != 1) {
			return;
		}
		INode topicNode = nodes.get(0);
		String data = this.serializeTopics(topics);
		topicNode.setAttributeValue(MULTI_HYPOTHESIS_TOPIC_ATTRIBUTE, data);
	}

	private List<Topic> deserializeTopics(String data) {
		try {
			List<Topic> topics = this.objectMapper.readValue(data, TOPIC_LIST);
			return topics;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String serializeTopics(List<Topic> topics) {
		try {
			String data = this.objectMapper.writeValueAsString(topics);
			return data;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<IHypothesesSet> getHypothesesFromGraph(PARSEGraphWrapper in) {
		IGraph graph = in.getGraph();
		List<INode> nodes = graph.getNodesOfType(graph.getNodeType(TopicExtraction.TOPICS_NODE_TYPE));
		if (nodes.size() != 1) {
			return new ArrayList<>();
		}
		INode topicNode = nodes.get(0);

		List<Topic> topics = this.deserializeTopics((String) topicNode.getAttributeValue(MULTI_HYPOTHESIS_TOPIC_ATTRIBUTE));
		topics.sort((t1, t2) -> -Double.compare(t1.getScore(), t2.getScore()));

		List<TopicHypothesis> hyps = new ArrayList<>();
		for (Topic t : topics) {
			if (hyps.size() >= this.maxHypothesis) {
				break;
			}
			hyps.add(new TopicHypothesis(t));
		}
		if (hyps.isEmpty()) {
			return List.of();
		}
		return Arrays.asList(new BasicHypothesesSet("Topic Hypotheses", HypothesisRange.SENTENCE, hyps, false));
	}

	@Override
	public List<IHypothesesSet> getHypothesesForNonHypothesesExecution(PARSEGraphWrapper in) {
		IGraph graph = in.getGraph();
		List<Topic> topics = TopicExtraction.getTopicsFromIGraph(graph);

		List<TopicHypothesis> hyps = new ArrayList<>();
		for (Topic t : topics) {
			hyps.add(new TopicHypothesis(t));
		}
		return Arrays.asList(new BasicHypothesesSet("Topic Hypotheses", HypothesisRange.SENTENCE, hyps, false));
	}

	@Override
	public void applyHypothesesToGraph(PARSEGraphWrapper in, List<IHypothesesSelection> hypotheses) {
		IGraph graph = in.getGraph();
		this.checkSelection(hypotheses);
		if (hypotheses.size() > 1) {
			throw new IllegalArgumentException("Too many HypothesesGroups are selected ..");
		}

		INodeType tokenType;
		if (graph.hasNodeType(TopicExtraction.TOPICS_NODE_TYPE)) {
			tokenType = graph.getNodeType(TopicExtraction.TOPICS_NODE_TYPE);
		} else {
			tokenType = graph.createNodeType(TopicExtraction.TOPICS_NODE_TYPE);
		}
		if (!tokenType.containsAttribute(TopicExtraction.TOPIC_ATTRIBUTE, "java.util.List")) {
			tokenType.addAttributeToType("java.util.List", TopicExtraction.TOPIC_ATTRIBUTE);
		}

		List<IHypothesis> hyps = hypotheses.get(0).getSelectedHypotheses();
		List<Topic> topics = hyps.stream().map(h -> ((TopicHypothesis) h).topic).collect(Collectors.toList());

		List<INode> nodes = graph.getNodesOfType(graph.getNodeType(TopicExtraction.TOPICS_NODE_TYPE));
		INode node;
		if (nodes.isEmpty()) {
			node = graph.createNode(graph.getNodeType(TopicExtraction.TOPICS_NODE_TYPE));
		} else {
			node = nodes.get(0);
		}
		node.setAttributeValue(TopicExtraction.TOPIC_ATTRIBUTE, topics);
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

	private static class TopicHypothesis implements IHypothesis {

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

}
