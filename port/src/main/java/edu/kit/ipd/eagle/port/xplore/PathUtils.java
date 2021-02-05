package edu.kit.ipd.eagle.port.xplore;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ipd.eagle.port.xplore.dto.PathDTO;
import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;

/**
 * Helper to generate {@link IPath Paths} from {@link ILayerEntry ILayerEntries}.
 *
 * @author Dominik Fuchss
 *
 */
public final class PathUtils {
	private PathUtils() {
		throw new IllegalAccessError();
	}

	/**
	 * Get paths by root element.
	 *
	 * @param  root the root entry
	 * @return      all paths
	 */
	public static List<IPath> getPaths(ILayerEntry root) {
		if (root == null) {
			return null;
		}
		List<IPath> paths = new ArrayList<>();
		addPaths(paths, root, new ArrayList<>());
		return paths;
	}

	private static void addPaths(List<IPath> paths, ILayerEntry currentEntry, List<ILayerEntry> predecessors) {
		List<ILayerEntry> newPredecessors = new ArrayList<>(predecessors);
		newPredecessors.add(currentEntry);

		if (currentEntry.getChildren() == null || currentEntry.getChildren().isEmpty()) {
			// Found Leaf .. build path ..
			PathDTO pathDTO = new PathDTO();
			pathDTO.setPrettyString(newPredecessors.toString());
			pathDTO.setPath(newPredecessors.toArray(ILayerEntry[]::new));
			paths.add(pathDTO);
			return;
		}

		for (ILayerEntry child : currentEntry.getChildren()) {
			addPaths(paths, child, newPredecessors);
		}

	}
}
