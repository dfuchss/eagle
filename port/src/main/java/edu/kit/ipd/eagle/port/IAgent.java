package edu.kit.ipd.eagle.port;

/**
 * The definition of agens for this project.
 *
 * @author Dominik Fuchss
 *
 * @param <DS> the type of data structure on which the agent operates
 */
public interface IAgent<DS extends IDataStructure<DS>> {
	/**
	 * Executes the agent on a certain data structure.
	 *
	 * @param input the input structure
	 * @return the <b>new</b> data structure that contains the results <b>ALWAYS:
	 *         {@code input != return}</b>
	 */
	DS execute(DS input);

	/**
	 * Get the name of the agent.
	 *
	 * @return the name of the agent
	 */
	String getName();

}
