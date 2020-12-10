package edu.kit.ipd.are.agentanalysis.port;

public interface IDataStructure<DS extends IDataStructure<DS>> extends Cloneable {
	DS clone();
}
