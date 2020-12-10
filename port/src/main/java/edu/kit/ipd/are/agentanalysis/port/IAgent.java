package edu.kit.ipd.are.agentanalysis.port;

public interface IAgent<DS extends IDataStructure<DS>> {

	DS execute(DS input);

}
