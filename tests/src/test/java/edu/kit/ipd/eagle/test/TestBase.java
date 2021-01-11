package edu.kit.ipd.eagle.test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;

import org.junit.Assume;
import org.junit.Before;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kit.ipd.eagle.impl.specification.parse.OntologySelectorSpec;
import edu.kit.ipd.eagle.port.util.Serialize;

/**
 * Base class for all tests.
 *
 * @author Dominik Fuchss
 *
 */
public class TestBase {

	private File targetDir;

	/**
	 * Setup the directory for the test results.
	 */
	@Before
	public void setupDir() {
		this.targetDir = new File("test-results" + File.separator + this.getClass().getSimpleName());
		if (this.targetDir.exists()) {
			this.clearDir(this.targetDir);
		}
		this.targetDir.mkdirs();
		System.err.println("Target dir for " + this.getClass().getSimpleName() + " is " + this.targetDir.getAbsolutePath());
	}

	private void clearDir(File dir) {
		var files = dir.listFiles();
		if (files != null) {
			Arrays.stream(files).filter(File::isFile).forEach(File::delete);
			Arrays.stream(files).filter(File::isDirectory).forEach(this::clearDir);
		}
		dir.delete();
	}

	/**
	 * Get a target file by filename.
	 *
	 * @param filename the filename
	 * @return a file for test results
	 */
	protected final File getTargetFile(String filename) {
		if (filename == null || filename.isBlank()) {
			return null;
		}
		File f = new File(this.targetDir.getAbsolutePath() + File.separator + filename);
		new File(f.getParent()).mkdirs();
		return f;
	}

	/**
	 * Read a resource.
	 *
	 * @param r the path to the resource
	 * @return the content of the resource
	 */
	protected final String readResource(String r) {
		Scanner scan = new Scanner(TestBase.class.getResourceAsStream(r));
		scan.useDelimiter("\\A");
		String data = scan.next();
		scan.close();
		return data;
	}

	/**
	 * Get all defined texts (from a former evaluation in the master thesis of Jan
	 * Keim).
	 *
	 * @return the texts
	 * @throws IOException iff loading went wrong
	 */
	public static final SortedMap<String, String> getTexts() throws IOException {
		ObjectMapper om = Serialize.getObjectMapper(true);
		return om.readValue(TestBase.class.getResourceAsStream("/texts/texts.json"), new TypeReference<SortedMap<String, String>>() {
		});
	}

	/**
	 * Load actor ontologies.
	 *
	 * @return the location string for actor ontologies
	 */
	protected final String loadActorOntologies() {
		return OntologySelectorSpec.loadOntologies("src/test/resources/ontology-selector/", //
				"robot.owl", "virtual_assistant.owl", "drone.owl", "lego_mindstorm.owl");
	}

	/**
	 * Load env ontologies.
	 *
	 * @return the location string for env ontologies
	 */
	protected final String loadEnvOntologies() {
		return OntologySelectorSpec.loadOntologies("src/test/resources/ontology-selector/", //
				"kitchen.owl", "bedroom.owl", "bar.owl", "laundry.owl", "garden.owl", "childrens_room.owl", "heating.owl", "music.owl");
	}

	/**
	 * Skip test because running in CI.
	 */
	public static void skipIffCI() {
		Assume.assumeFalse("Running in CI", Objects.equals("true", System.getenv("CI")));
	}
}
