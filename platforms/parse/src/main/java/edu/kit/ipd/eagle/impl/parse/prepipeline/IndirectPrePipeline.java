package edu.kit.ipd.eagle.impl.parse.prepipeline;

import java.util.Properties;

import edu.kit.ipd.eagle.impl.parse.PARSEGraphWrapper;
import edu.kit.ipd.indirect.graphBuilder.GraphBuilder;
import edu.kit.ipd.indirect.textSNLP.Stanford;
import edu.kit.ipd.indirect.textSNLP.TextSNLP;
import edu.kit.ipd.indirect.tokenizer.Tokenizer;
import edu.kit.ipd.parse.luna.data.PrePipelineData;
import edu.kit.ipd.parse.luna.tools.ConfigManager;

/**
 * Defines the default pipline for the INDIRECT Project.
 *
 * @author Dominik Fuchss
 *
 */
public final class IndirectPrePipeline implements IPrePipeline {
	/**
	 * Create the pipeline for INDIRECT.
	 */
	public IndirectPrePipeline() {
		Properties props = ConfigManager.getConfiguration(Stanford.class);
		props.setProperty("TAGGER_MODEL", "/edu/stanford/nlp/models/pos-tagger/english-bidirectional/english-bidirectional-distsim.tagger");
		props.setProperty("LEMMAS", "seconds/NNS/second;milliseconds/NNS/millisecond;hours/NNS/hour;minutes/NNS/minute;months/NNS/month;years/NNS/year");
	}

	@Override
	public PrePipelineMode getMode() {
		return PrePipelineMode.INDIRECT;
	}

	@Override
	public PARSEGraphWrapper createGraph(String text) {
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.init();
		TextSNLP snlp = new TextSNLP();
		snlp.init();
		GraphBuilder graphBuilder = new GraphBuilder();
		graphBuilder.init();

		PrePipelineData ppd = new PrePipelineData();
//		ppd.setMainHypothesis(StringToHypothesis.stringToMainHypothesis(text, true));
		ppd.setTranscription(text);

		try {
			tokenizer.exec(ppd);
			snlp.exec(ppd);
			graphBuilder.exec(ppd);
			return new PARSEGraphWrapper(ppd.getGraph(), text, PrePipelineMode.INDIRECT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
