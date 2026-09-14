JDD performance
****************

JDD is written in Java, and raw performance was not the main priority during design.

Still, JDD is a very efficient BDD package. In some cases, the computation speed is comparable to CUDD and BuDDy. Due to a new cache scheme, JDD sometimes even outperforms those two (this depends very much on the size and type of the problem being solved). The main disadvantage of using JDD is its higher memory usage.


If you still need a faster BDD package, check out `JBDD <https://bitbucket.org/vahidi/jbdd>`_, a Java interface to BuDDy and CUDD.


Benchmarks
----------

Benchmarking is hard, especially when performance can vary greatly with minor adjustments.
Still, when this project started many years ago, we ran some quick tests to demonstrate that JDD was fast enough::


  Package  Slow ratio N queens        Slow ratio, CNF SAT
              (12 x Queens)        (aim-100-6_0-yes1-2)
  BuDDy           1.0                        1.6
  CUDD            1.65                       ?
  SableJBDD       > 10                       ?
  JavaBDD         2.5                        5.15
  JDD/BDD         1.55                       1.0
  JDD/ZBDD        0.9                        N/A
  JDD/ZBDD-CSP    0.29                       N/A
  JDD/GSAT       N/A                        < 0.02

Notes:

1. SableJBDD lacks garbage collection
2. At that time, JavaBDD was in alpha and has improved since then.
3. GSAT is no longer included in JDD.

BDD Traces
==========

A BDD-trace is a series of calls to a BDD library recorded at operator level (AND, OR, etc.).
With a trace driver, you can repeat these operations with another BDD package.
This allows you to easily compare the performance of two BDD packages.

The JDD distribution includes a trace driver (BDDTrace.java) plus the following traces:

* The original traces in Bwolen Yang's trace driver [1].
* ISCAS85 traces (including the C6288 multiplier) by Yirng-An Chen.
* Superscalar Suite 1.0 by Miroslav Velev.

See the *profile.sh* script for more information.

---

  [1] Some of these models are explained in the NuSMV manual.
