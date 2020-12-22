package edu.kit.ipd.eagle.port.xplore.dto;

import java.util.List;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.xplore.IExplorationResult;
import edu.kit.ipd.eagle.port.xplore.IPath;
import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;

/**
 * Defines the data transfer object for {@link IPath}.
 *
 * @author Dominik Fuchss
 *
 */
public class PathDTO implements IPath {

	private static final long serialVersionUID = -5934241158961679119L;

	private String prettyString;
	private ILayerEntry[] path;

	/**
	 * Setter for {@link #getPrettyString()}.
	 *
	 * @param prettyString pretty string
	 */
	public void setPrettyString(String prettyString) {
		this.prettyString = prettyString;
	}

	@Override
	public String getPrettyString() {
		return this.prettyString;
	}

	/**
	 * Setter for {@link #getPath()}.
	 *
	 * @param path the path
	 */
	public void setPath(ILayerEntry[] path) {
		this.path = path;
	}

	@Override
	public ILayerEntry[] getPath() {
		return this.path;
	}

	@Override
	public IExplorationResult toExplorationResult(String text) {
		ILayerEntry step = null;
		for (int i = this.path.length - 1; i >= 0; i--) {
			ILayerEntry es = this.createNewLayerEntry(//
					String.valueOf(i), this.path[i].getAgent(), this.path[i].getHypotheses(), this.path[i].getSelectionsFromBefore(), step == null ? List.of() : List.of(step));
			step = es;
		}
		ExplorationResultDTO result = new ExplorationResultDTO();
		result.setInputText(text);
		result.setExplorationRoot(step);
		return result;
	}

	private ILayerEntry createNewLayerEntry(String id, String agent, List<IHypothesesSet> hypotheses, List<IHypothesesSelection> selectionsFromBefore, List<ILayerEntry> children) {
		LayerEntryDTO entry = new LayerEntryDTO();
		entry.setId(id);
		entry.setAgent(agent);
		entry.setSelectionsFromBefore(selectionsFromBefore);
		entry.setHypotheses(hypotheses);
		entry.setChildren(children);
		return entry;
	}
}
