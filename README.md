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
* [IAgentSpecification](port/src/main/java/edu/kit/ipd/eagle/port/IAgentSpecification.java): Specifies the dependencies of a "normal" agent of a multi-agent system.
* [IAgentHypothesisSpecification](port/src/main/java/edu/kit/ipd/eagle/port/hypothesis/IAgentHypothesisSpecification.java): Specifies the dependencies of an agent that can deal with hypotheses. Such an agent is able to read and write hypotheses to a data structure. A hypothesis is defined by a value and a confidence.
* [IHypothesis](port/src/main/java/edu/kit/ipd/eagle/port/hypothesis/IHypothesis.java): Defines a hypothesis that can be created and/or used by agents.
* [IInformationId](port/src/main/java/edu/kit/ipd/eagle/port/IInformationId.java): In order to execute the agents of an MAS in the right order, you have to define the provided and required information of an agent. See [Example](#example) for further information.

For the exploration of hypotheses, the following interfaces are important (a picture of what is described is included for illustration):
![Layers](.github/img/Layers.png)
* [ILayer](port/src/main/java/edu/kit/ipd/eagle/port/xplore/layer/ILayer.java): During exploration, specified agents are executed in a valid sequence. During this process, agents can generate hypotheses. For a run of the exploration, a layer for an agent includes the input data, the hypotheses generated by the agents, and the selections of hypotheses made together with the resulting data structures.
* [ILayerEntry](port/src/main/java/edu/kit/ipd/eagle/port/xplore/layer/ILayerEntry.java): A layer entry is an entry within a layer ( :) ). One can equate an entry with a path segment within the exploration. Within a layer entry there is exactly one data of origin, the generated hypotheses, as well as the result data structures for the agent of the layer that were created by different selections.
* [ISelectionProvider](port/src/main/java/edu/kit/ipd/eagle/port/xplore/selection/ISelectionProvider.java): A selection provider is used in exploration to obtain different selections (parts of these hypotheses) from a set of hypotheses and to explore further based on these.
* [IRatingFunction](port/src/main/java/edu/kit/ipd/eagle/port/xplore/rating/IRatingFunction.java): A rating function can rate a set of paths. Paths here are `ILayerEntry[]`.
* [IExplorationResult](port/src/main/java/edu/kit/ipd/eagle/port/xplore/IExplorationResult.java): The result of the exploration is summarized in an `IExplorationResult`. It contains the input text and the first layer entry.
* [IExploration](port/src/main/java/edu/kit/ipd/eagle/port/xplore/IExploration.java): Defines the interface which is provided for exploring.

### Platforms
All agents depend on a certain platform that defines their operating data structures and the way they are executed.
Therefore, you need to define the platform of the agents you want to evaluate.
As an example the platform for [PARSE agents](https://code.ipd.kit.edu/weigelt/parse) is defined in this project (see [platforms/parse](platforms/parse/src/main/java/edu/kit/ipd/eagle/impl/platforms/parse)).
In order to work with agents in a certain platform, you have to implement at least the following interfaces:
* [IAgent](port/src/main/java/edu/kit/ipd/eagle/port/IAgent.java): The definition of an agent that operates on a certain data structure.
* [IDataStructure](port/src/main/java/edu/kit/ipd/eagle/port/IDataStructure.java): The definition of the data structure.
* [IAgentSpecification](port/src/main/java/edu/kit/ipd/eagle/port/IAgentSpecification.java) resp. [IAgentHypothesisSpecification](port/src/main/java/edu/kit/ipd/eagle/port/hypothesis/IAgentHypothesisSpecification.java) for certain agents you want to evaluate. The specifications of the example (PARSE) are described in [Specifications](#specifications).

### Specifications
Every agent that shall be executed by the EAGLE System needs a specification.
Some example specifications are provided in certain modules of the [Specification Module](specification/):
* [indirect](specification/indirect/src/main/java/edu/kit/ipd/eagle/impl/specification/indirect): Specifications of some [INDIRECT](https://code.ipd.kit.edu/hey/indirect) agents that operate on the PARSE Platform. No agent provides hypotheses. Therefore, you will only find `IAgentSpecification` in the module.


* [parse](specification/parse/src/main/java/edu/kit/ipd/eagle/impl/specification/parse): Specifications of some [PARSE](https://code.ipd.kit.edu/weigelt/parse) agents that operate on the PARSE Platform. No agent provides hypotheses. Therefore, you will only find `IAgentSpecification` in the module.


* [parse-hypotheses](specification/parse-hypotheses/src/main/java/edu/kit/ipd/eagle/impl/specification/parse): Specifications of some [PARSE](https://code.ipd.kit.edu/weigelt/parse) agents that operate on the PARSE Platform and are able to generate hypotheses. Therefore, you will find `IAgentSpecification` in the module as well as `IAgentHypothesisSpecification` in a subdirectory.

### Exploration (eXplore)
The [xplore](xplore/src/main/java/edu/kit/ipd/eagle/impl/xplore) module contains the necessary implementations to explore the search space of hypotheses.

Important classes and/or packages are:

* [Selection Providers](xplore/src/main/java/edu/kit/ipd/eagle/impl/xplore/selection): Some basic realizations of `ISelectionProvider` (cf. section 6.3 in [Thesis](https://doi.org/10.5445/IR/1000126806))
  * `FullExploration`
  * `RandomHypothesis`
  * `TopXConfidence`
  * `TopXSlidingWindow`
  * `SameWordSameDecision` (decorator that ensures that equal words have equal hypotheses selections)


* [Rating Functions](xplore/src/main/java/edu/kit/ipd/eagle/impl/xplore/rating): Some basic realizations of `IRatingFunction` ..
  * `NormalizedAggregate`: Configurable Rating function that normalizes hypotheses per Layer and determine the final scores of path by aggregation of scores of layer entries.


* `LayeredExploration` basic implementation of an exploration. Also take a look at the following subclasses:
  * `SimpleExploration`: out of the box exploration
  * `SpecificExploration`: exploration that has to be configured to use a certain selection provider per layer.

### Tests

## Explorer

## Evaluator

## Example
This repository implements the approach for the PARSE Platform and some PARSE agents.
They can be seen as an example, how to work with EAGLE.
In the following, the needed steps to adopt this framework to other platforms or agents will be stated.

### PARSE Platform (Example)

### PARSE Specifications (Example)