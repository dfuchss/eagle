package edu.kit.ipd.are.agentanalysis.port;

/**
 * Defines a static configuration class for the Agent Analysis Project.
 *
 * @author Dominik Fuchss
 *
 */
public final class AgentAnalysisConfiguration {
	private AgentAnalysisConfiguration() {
		throw new IllegalAccessError();
	}

	private static boolean overridePrePiplineRestricitions = false;

	/**
	 * Defines whether system will check the compatibility of
	 * {@link IAgentSpecification} and {@link IPrePipeline}.
	 *
	 * @param override set to {@code true} to disable checks
	 * @see IAgentSpecification#getMode()
	 * @see IPrePipeline#getMode()
	 */
	public static void setOverridePrePipelineRestrictions(boolean override) {
		overridePrePiplineRestricitions = override;
	}

	/**
	 * Checks whether the system shall check the compatibility of
	 * {@link IAgentSpecification} and {@link IPrePipeline}.
	 *
	 * @return {@code true} iff no checks intended
	 */
	public static boolean isOverridePrePiplineRestricitions() {
		return overridePrePiplineRestricitions;
	}
}
