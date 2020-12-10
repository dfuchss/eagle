package edu.kit.ipd.are.agentanalysis.port.xplore;

import edu.kit.ipd.are.agentanalysis.port.IDataStructure;

public interface IInitialData<DS extends IDataStructure<DS>> {
	String getText();

	DS getData();
}
