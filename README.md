# JKendrick

## How to install

### Importing in Eclipse works well using 

Import\Git\Project from git (using smart import)

Obviously dependencies need to be installed and since this is not a Maven or Ant project it must be checked what Eclipse installs.

### Dependencies 

#### Apache Commons RungeKuttaIntegrator

Apache's implementation of RK4 is used: org.apache.commons.math3.ode.nonstiff.RungeKuttaIntegrator

To add Apache commons to an Eclipse project: 
>https://docs.blackberry.com/en/development-tools/blackberry-web-services-for-blackberry-uem/12_10/java-development-guide/bma1439578367525/bma1439578368853

To use RungeKuttaIntegrator and other solvers from Apache Commons: 
>[https://commons.apache.org/proper/commons-math/userguide/ode.html](https://commons.apache.org/proper/commons-math/userguide/ode.html)

>"The user should describe his problem in his own classes which should implement the FirstOrderDifferentialEquations interface.
 Then they should pass it to the integrator they prefer among all the classes that implement the FirstOrderIntegrator interface."
 
So in the very first example SIR_ODE implements FirstOrderDifferentialEquations.

The SIR_ODE.getDimension method return the size of the array holding the cardinality of each compartment.
So for this simple SIR example it is 3 because the compartments are  S, I and R with as many differential equations for dS/dt dI/dt and dR/dt.

#### XChart
>[https://github.com/knowm/XChart](https://github.com/knowm/XChart)

It is not mandatory to create a Maven project to use XChart

### Importing JKendrick in Eclipse
Check that you have correctly configured Git by following this procedure 
> [https://eclipsesource.com/blogs/tutorials/egit-tutorial/](https://eclipsesource.com/blogs/tutorials/egit-tutorial/)

Then, follow this step

> Import\Git\Project from git (using smart import)

## Mass-action vs standard incidence
Both kinds of incidence are only equivalent if N is fixed. Their assumptions are different which matter when the population gets large.

### Mass-Action incidence 
Mass action  assumes transmission is unbounded when the number of infected people grows.

dS/dt = - beta IS 

Caveat: beta typically has to be changed when N is changed.

### Standard incidence
Standard incidence assumes transmisson depends on the number of contacts with infected individuals and is bounded when N grows.

dS/dt = - beta/N IS

Note that bata/N is automatically adapted when N changes.

## Examples from Keeling and Rohani

### SIR
The example is adapted from  [https://homepages.warwick.ac.uk/~masfz/ModelingInfectiousDiseases/Chapter2/Program_2.1/index.html](https://homepages.warwick.ac.uk/~masfz/ModelingInfectiousDiseases/Chapter2/Program_2.1/index.html).

#### The (Pharo) Kendrick version
https://github.com/KendrickOrg/kendrick/wiki/Basic-SIR-(Benchmark-Model-1)
https://github.com/KendrickOrg/kendrick/blob/ba11051d2c8976bb27a12da0e3e51650cee99e51/src/Kendrick-Examples/KEDeterministicExamples.class.st#L1183
![SIR Model with Pharo code](https://github.com/YvanGuifo/PhdThesisYvanGuifo_2019-2022/blob/master/notes-lectures/Images%20de%20document%20de%20note%20de%20lecture/Images/SIRModelPharoCode.png)

#### Visualization 
![SIR Model with Java code](https://github.com/YvanGuifo/PhdThesisYvanGuifo_2019-2022/blob/master/notes-lectures/Images%20de%20document%20de%20note%20de%20lecture/Images/SIRModelJavaCode.png)

### SIS

#### SEIR


