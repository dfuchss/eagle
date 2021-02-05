package edu.kit.ipd.eagle.test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSelection;
import edu.kit.ipd.eagle.port.hypothesis.IHypothesesSet;
import edu.kit.ipd.eagle.port.xplore.IExplorationResult;
import edu.kit.ipd.eagle.port.xplore.IPath;
import edu.kit.ipd.eagle.port.xplore.dto.ExplorationResultDTO;
import edu.kit.ipd.eagle.port.xplore.dto.HypothesesSelectionDTO;
import edu.kit.ipd.eagle.port.xplore.dto.HypothesesSetDTO;
import edu.kit.ipd.eagle.port.xplore.dto.LayerEntryDTO;
import edu.kit.ipd.eagle.port.xplore.layer.ILayerEntry;

/**
 * Tests for Data Transfer Objects {@link edu.kit.ipd.eagle.port.xplore.dto}.
 *
 * @author Dominik Fuchss
 *
 */
public class DTOTest {
	/**
	 * Test getters and setters of {@link ExplorationResultDTO}. Ensure stable behavior.
	 */
	@Test
	public void testGetterSetterOfExplorationResult() {
		ExplorationResultDTO exr = new ExplorationResultDTO();
		Assert.assertNull(exr.getId());
		Assert.assertNull(exr.getExplorationRoot());
		Assert.assertNull(exr.getPaths());

		// Now create target values ..
		String id = "ID";
		ILayerEntry entry = new LayerEntryDTO();

		// Invoke setters
		exr.setId(id);
		exr.setExplorationRoot(entry);

		// Now check whether setting works ..
		Assert.assertEquals(id, exr.getId());
		Assert.assertEquals(entry, exr.getExplorationRoot());
		List<IPath> paths = exr.getPaths();
		Assert.assertEquals(1, paths.size());
		Assert.assertEquals(1, paths.get(0).getPath().length);
		Assert.assertEquals(entry, paths.get(0).getPath()[0]);
	}

	/**
	 * Test Serialization and Deserialization of {@link ExplorationResultDTO}. Ensure stable behavior.
	 *
	 * @throws IOException iff serialization does not work
	 */
	@Test
	public void testSerializeOfExplorationResult() throws IOException {
		ExplorationResultDTO exr = new ExplorationResultDTO();
		Assert.assertNull(exr.getId());
		Assert.assertNull(exr.getExplorationRoot());
		Assert.assertNull(exr.getPaths());

		// Now create target values ..
		String id = "ID";
		LayerEntryDTO entry = new LayerEntryDTO();
		entry.setId("LAYER_ENTRY_ID");

		// Invoke setters
		exr.setId(id);
		exr.setExplorationRoot(entry);

		// Serialize and Deserialize
		File f = File.createTempFile("test", ".json");
		f.deleteOnExit();
		ExplorationResultDTO.store(exr, f);

		IExplorationResult exrNew = ExplorationResultDTO.load(f);

		// Now check whether setting works ..
		Assert.assertEquals(id, exrNew.getId());
		Assert.assertEquals(entry.getId(), exrNew.getExplorationRoot().getId());
		List<IPath> paths = exrNew.getPaths();
		Assert.assertEquals(1, paths.size());
		Assert.assertEquals(1, paths.get(0).getPath().length);
		Assert.assertEquals(entry.getId(), paths.get(0).getPath()[0].getId());
	}

	/**
	 * Test getters and setters of {@link LayerEntryDTO}. Ensure stable behavior.
	 */
	@Test
	public void testGetterSetterOfLayerEntry() {
		LayerEntryDTO le = new LayerEntryDTO();
		Assert.assertNull(le.getAgent());
		Assert.assertNull(le.getId());
		Assert.assertNull(le.getStateRepresentationBeforeExecution());
		Assert.assertNull(le.getChildren());
		Assert.assertNull(le.getHypotheses());
		Assert.assertThrows(UnsupportedOperationException.class, () -> le.getPathToRoot());
		Assert.assertNull(le.getSelectionsFromBefore());
		Assert.assertThrows(UnsupportedOperationException.class, () -> le.isLeaf());

		// Now create target values ..
		String agent = "AGENT";
		String id = "ID";
		String stateBeforeExec = "STATE";
		List<ILayerEntry> children = List.of(new LayerEntryDTO(), new LayerEntryDTO());
		children.forEach(c -> ((LayerEntryDTO) c).setId(id + c.hashCode()));
		List<IHypothesesSet> hypos = List.of(new HypothesesSetDTO(), new HypothesesSetDTO());
		List<IHypothesesSelection> sel = List.of(new HypothesesSelectionDTO(), new HypothesesSelectionDTO());

		// Invoke setters
		le.setAgent(agent);
		le.setId(id);
		le.setStateRepresentationBeforeExecution(stateBeforeExec);
		le.setChildren(children);
		le.setHypotheses(hypos);
		le.setSelectionsFromBefore(sel);

		// Now check whether setting works ..
		Assert.assertEquals(agent, le.getAgent());
		Assert.assertEquals(id, le.getId());
		Assert.assertEquals(stateBeforeExec, le.getStateRepresentationBeforeExecution());
		Assert.assertEquals(new HashSet<>(children), new HashSet<>(le.getChildren()));
		Assert.assertEquals(hypos, le.getHypotheses());
		Assert.assertThrows(UnsupportedOperationException.class, () -> le.getPathToRoot());
		Assert.assertEquals(sel, le.getSelectionsFromBefore());
		Assert.assertThrows(UnsupportedOperationException.class, () -> le.isLeaf());
	}
}
