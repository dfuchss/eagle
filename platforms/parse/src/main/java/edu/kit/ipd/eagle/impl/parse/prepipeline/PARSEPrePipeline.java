package edu.kit.ipd.eagle.impl.parse.prepipeline;

import edu.kit.ipd.eagle.impl.parse.GraphUtils;
import edu.kit.ipd.eagle.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.parse.graphBuilder.GraphBuilder;
import edu.kit.ipd.parse.luna.data.PrePipelineData;
import edu.kit.ipd.parse.luna.tools.ConfigManager;
import edu.kit.ipd.parse.luna.tools.StringToHypothesis;
import edu.kit.ipd.parse.ner.NERTagger;
import edu.kit.ipd.parse.ontology_connection.Domain;
import edu.kit.ipd.parse.shallownlp.ShallowNLP;
import edu.kit.ipd.parse.srlabeler.SRLabeler;

/**
 * Defines the default pipline for the PARSE Project.
 *
 * @author Dominik Fuchss
 *
 */
public final class PARSEPrePipeline implements IPrePipeline {
	/**
	 * Create the pipeline for PARSE.
	 */
	public PARSEPrePipeline() {
		var props = ConfigManager.getConfiguration(Domain.class);
		props.setProperty("ONTOLOGY_PATH", "/ontology.owl");
		props.setProperty("SYSTEM", "System");
		props.setProperty("METHOD", "Method");
		props.setProperty("PARAMETER", "Parameter");
		props.setProperty("DATATYPE", "DataType");
		props.setProperty("VALUE", "Value");
		props.setProperty("STATE", "State");
		props.setProperty("OBJECT", "Object");
		props.setProperty("SYSTEM_HAS_METHOD", "hasMethod");
		props.setProperty("STATE_ASSOCIATED_STATE", "associatedState");
		props.setProperty("STATE_ASSOCIATED_OBJECT", "associatedObject");
		props.setProperty("STATE_CHANGING_METHOD", "changingMethod");
		props.setProperty("METHOD_CHANGES_STATE", "changesStateTo");
		props.setProperty("METHOD_HAS_PARAMETER", "hasParameter");
		props.setProperty("OBJECT_HAS_STATE", "hasState");
		props.setProperty("OBJECT_SUB_OBJECT", "subObject");
		props.setProperty("OBJECT_SUPER_OBJECT", "superObject");
		props.setProperty("PARAMETER_OF_DATA_TYPE", "ofDataType");
		props.setProperty("DATATYPE_HAS_VALUE", "hasValue");
		props.setProperty("PRIMITIVE_TYPES", "String,int,double,float,short,char,boolean,long");
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.PARSE;
	}

	@Override
	public PARSEGraphWrapper createGraph(String in) {
		String text = GraphUtils.normalize(in);

		GraphBuilder graphBuilder = new GraphBuilder();
		graphBuilder.init();
		NERTagger nerTagger = new NERTagger();
		nerTagger.init();
		SRLabeler srLabeler = new SRLabeler();
		srLabeler.init();
		ShallowNLP snlp = new ShallowNLP();
		snlp.init();

		PrePipelineData ppd = new PrePipelineData();
		ppd.setMainHypothesis(StringToHypothesis.stringToMainHypothesis(text, true));

		try {
			snlp.exec(ppd);
			nerTagger.exec(ppd);
			srLabeler.exec(ppd);
			graphBuilder.exec(ppd);
			return new PARSEGraphWrapper(ppd.getGraph(), text, PrePipelineMode.PARSE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
