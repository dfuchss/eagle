# Environment for Analysis of AGents based on Led Exploration (EAGLE)
This project contains an environment to explore the search space defined by hypotheses of agents of multi-agent systems (MAS).
The EAGLE is mainly based on the Master Thesis of Dominik Fuchss (cf. [Thesis](https://doi.org/10.5445/IR/1000126806)).
Therefore, this work can give you insight into the actual usage of this project.

## Structure of EAGLE
This project is structured using maven modules. In the following the structure is explained.

### Port
The port module defines all necessary modules to work with EAGLE.
Furthermore, common classes are defined as well.
Especially the following interfaces and classes are important:
* [IAgentSpecification](port/src/main/java/edu/kit/ipd/eagle/port/IAgentSpecification.java): Defines a "normal" agent of a multi-agent system
* [IAgentHypothesisSpecification](port/src/main/java/edu/kit/ipd/eagle/port/hypothesis/IAgentHypothesisSpecification.java): Defines an agent that can deal with hypotheses. Such an agent is able to read and write hypotheses to a data structure. Thereby, a hypothesis is defined by a value and a confidence.
* [IHypothesis](port/src/main/java/edu/kit/ipd/eagle/port/hypothesis/IHypothesis.java): Defines a hypothesis that can be created and/or used by agents
* [IInformationId](port/src/main/java/edu/kit/ipd/eagle/port/IInformationId.java): In order to execute the agents of an MAS in the right order, you have to define the provided and required information of an agent. See [Example](#Example) for further information.

For the exploration of hypotheses, the following interfaces are important (a picture of what is described is included for illustration):
![Layers](.github/img/Layers.png)
* ILayer
* ILayerEntry
* ISelectionProvider
* IRatingFunction
* IExplorationResult
* IExploration

### Platforms
All agents depend on a certain platform that defines their operating data structures and the way they are executed.
Therefore, you need to define the platform of the agents you want to evaluate.
As an example the platform for [PARSE agents](https://code.ipd.kit.edu/weigelt/parse) is defined in this project (see [platforms/parse](platforms/parse/src/main/java/edu/kit/ipd/eagle/impl/parse)).

### Specifications

### Exploration (eXplore)

### Tests

## Explorer

## Evaluator

## Example
This repository implements the approach for the PARSE Platform and some PARSE agents.
They can be seen as an example, how to work with EAGLE.
In the following, the needed steps to adopt this framework to other platforms or agents will be stated.
