package edu.kit.ipd.eagle.impl.parse;

import edu.kit.ipd.eagle.port.IAgent;
import edu.kit.ipd.eagle.port.IAgentSpecification;
import edu.kit.ipd.eagle.port.IInformationId;

/**
 * This enum defines the different information that are needed or provided by
 * {@link IAgent Agents}.
 *
 * @author Dominik Fuchss
 * @see IAgentSpecification#getRequiresIds()
 * @see IAgentSpecification#getProvideIds()
 */
public enum PARSEInformationId implements IInformationId {

	/////////////// PARSE ///////////////////
	/**
	 * Actions in a text. e.g the results of ActionRecognizer.
	 */
	ACTIONS,
	/**
	 * Concurrent actions in a text. e.g. the results of ConcurrencyAgent.
	 */
	CONCURRENCY,
	/**
	 * Conditions in a text. e.g. the results of ConditionDetector.
	 */
	CONDITIONS,
	/**
	 * Loop actions in a text. e.g. the results of LoopDetectionAgent.
	 */
	LOOP,
	/**
	 * Information on Wordsenses. e.g. the results of Wsd.
	 */
	WORD_SENSE_DISAMBIGUATION,
	/**
	 * Information on Locations, Entities, Actions, ... e.g. results of
	 * ContextAnalyzer.
	 */
	CONTEXT,
	/**
	 * Information on Coreferences. e.g. results of CorefAnalyzer.
	 */
	COREF,

	/**
	 * Information on Methods and Method calls. e.g. the results of
	 * MethodSynthesizer.
	 */
	METHODS,

	/////////////// JAN ///////////////////
	/**
	 * Information on Wordsenses with concepts from Wikipedia. e.g. the results of
	 * WordSenseDisambiguation.
	 */
	WIKI_WSD,
	/**
	 * Information on Topics of a Text. e.g. the results of TopicExtraction.
	 */
	TOPICS,
	/**
	 * Information on matching ontologies for a text. e.g. the results of
	 * OntologySelector.
	 */
	ONTOLOGY,

	/////////////// INDIRECT ///////////////////
	/**
	 * Information on Named Entities for a text. e.g. the results of TextNERTagger.
	 */
	NER,
	/**
	 * Information on Dependencies between words for a text. e.g. the results of
	 * DepParser.
	 */
	DEPENDENCIES,
	/**
	 * Information on entities in a text. e.g. the results of EntityRecognizer.
	 */
	ENTITIES,
	/**
	 * Information on concepts in a text. e.g. the results of Conceptualizer.
	 */
	CONCEPTS,
	/**
	 * Information on constituency in a text. e.g. the results of ConstParser.
	 */
	CONSTITUENCY,

	/**
	 * Information on mappings between model an text. e.g. the results of
	 * WsdLinkAgent.
	 */
	MODEL_MAPPING

}