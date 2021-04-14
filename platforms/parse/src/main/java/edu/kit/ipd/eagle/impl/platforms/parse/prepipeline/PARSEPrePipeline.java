package edu.kit.ipd.eagle.impl.platforms.parse.prepipeline;

import edu.kit.ipd.eagle.impl.platforms.parse.GraphUtils;
import edu.kit.ipd.eagle.impl.platforms.parse.PARSEGraphWrapper;
import edu.kit.ipd.pronat.graph_builder.GraphBuilder;
import edu.kit.ipd.pronat.ner.NERTagger;
import edu.kit.ipd.pronat.prepipedatamodel.PrePipelineData;
import edu.kit.ipd.pronat.prepipedatamodel.tools.StringToHypothesis;
import edu.kit.ipd.pronat.shallow_nlp.ShallowNLP;
import edu.kit.ipd.pronat.srl.SRLabeler;

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
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.PARSE;
	}

	@Override
	public PARSEGraphWrapper createGraph(String inputText) {
		String text = GraphUtils.normalize(inputText);

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
