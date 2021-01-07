package edu.kit.ipd.eagle.port.xplore.dto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import edu.kit.ipd.eagle.port.util.Serialize;
import edu.kit.ipd.eagle.port.xplore.IExplorationResult;
import edu.kit.ipd.eagle.port.xplore.IPath;
import edu.kit.ipd.eagle.port.xplore.PathUtils;
import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;

/**
 * Defines the data transfer object for {@link IExplorationResult}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ExplorationResultDTO implements IExplorationResult {
	/**
	 * Load exploration result from file.
	 *
	 * @param file the file
	 * @return the exploration result
	 * @throws IOException iff deserialization was not successful
	 */
	public static IExplorationResult load(File file) throws IOException {
		return Serialize.getObjectMapperForGetters(true).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(file, ExplorationResultDTO.class);
	}

	/**
	 * Store {@link IExplorationResult} to file.
	 *
	 * @param data   the exploration result
	 * @param target the target file
	 * @throws IOException iff serialization was not successful
	 */
	public static void store(IExplorationResult data, File target) throws IOException {
		Serialize.getObjectMapperForGetters(true).writeValue(target, data);
	}

	private static final long serialVersionUID = 5459294884116324175L;
	private ILayerEntry root;
	private String id;

	/**
	 * Setter of the exploration root.
	 *
	 * @param root the exploration root
	 */
	@JsonDeserialize(as = LayerEntryDTO.class)
	public void setExplorationRoot(ILayerEntry root) {
		this.root = root;
	}

	@Override
	public ILayerEntry getExplorationRoot() {
		return this.root;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Setter of the input text.
	 *
	 * @param id the input text
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int layer = 0;
		List<ILayerEntry> layerSteps = new ArrayList<>();
		layerSteps.add(this.root);

		while (!layerSteps.isEmpty()) {
			result.append("Layer " + layer + ":\n");
			List<ILayerEntry> nextLayer = new ArrayList<>();
			for (var step : layerSteps) {
				result.append(step);
				result.append("\n");
				nextLayer.addAll(step.getChildren());
			}
			layerSteps.clear();
			layerSteps.addAll(nextLayer);
			layer++;
		}
		return result.toString();
	}

	@Override
	public List<IPath> getPaths() {
		return PathUtils.getPaths(this.getExplorationRoot());
	}
}
