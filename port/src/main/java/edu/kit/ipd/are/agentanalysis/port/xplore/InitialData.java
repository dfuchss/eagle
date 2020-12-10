package edu.kit.ipd.are.agentanalysis.port.xplore;

import edu.kit.ipd.are.agentanalysis.port.IDataStructure;

public final class InitialData<DS extends IDataStructure<DS>> implements IInitialData<DS> {

	private final String text;
	private final DS data;

	public InitialData(String text, DS data) {
		this.text = text;
		this.data = data;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public DS getData() {
		return this.data;
	}

}
