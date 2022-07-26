# JKendrick

## How to install
###Importing
Importing in Eclipse works well using 

Import\Git\Project from git (using smart import)

Obviously dependencies need to be installed and since this is not a Maven or Ant project it must be checked what Eclipse installs.

### Dependencies 

#### Apache Commons RungeKuttaIntegrator
RK4 is used: org.apache.commons.math3.ode.nonstiff.RungeKuttaIntegrator

See https://commons.apache.org/proper/commons-math/userguide/ode.html

"The user should describe his problem in his own classes which should implement the FirstOrderDifferentialEquations interface.
 Then they should pass it to the integrator they prefer among all the classes that implement the FirstOrderIntegrator interface."
 
 So in the very first example SIR_ODE implements FirstOrderDifferentialEquations.
 
 
The SIR_ODE.getDimension method return the size of the array holding the cardinality of each compartment.
So for this simple SIR example it is 3 because the compartments are  S, I and R with as many differential equations for dS/dt dI/dt and dR/dt.

#### XChart
https://github.com/knowm/XChart
It is not mandatory to create a Maven project to use XChart


