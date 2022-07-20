# JKendrick

## How to install

### Dependencies 

#### Apache Commons RungeKuttaIntegrator

RK4 is used: org.apache.commons.math3.ode.nonstiff.RungeKuttaIntegrator

See [https://commons.apache.org/proper/commons-math/userguide/ode.html](https://commons.apache.org/proper/commons-math/userguide/ode.html)

"The user should describe his problem in his own classes which should implement the FirstOrderDifferentialEquations interface.
 Then they should pass it to the integrator they prefer among all the classes that implement the FirstOrderIntegrator interface."
 
 So in the very first example SIR_ODE implements FirstOrderDifferentialEquations.
 
 
The SIR_ODE.getDimension method return the size of the array holding the cardinality of each compartment.
So for this simple SIR example it is 3 because the compartments are  S, I and R with as many differential equations for dS/dt dI/dt and dR/dt.

#### XChart

[https://github.com/knowm/XChart](https://github.com/knowm/XChart)
It is not mandatory to create a Maven project to use XChart

###Importing

> Check that you have correctly configured Git by following this procedure [https://eclipsesource.com/blogs/tutorials/egit-tutorial/](https://eclipsesource.com/blogs/tutorials/egit-tutorial/)

After that follow this step

> Importing in Elipse works well using  Import\Git\Project from git (using smart import)

It needs to access XChart
