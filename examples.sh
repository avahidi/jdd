#!/bin/sh
# This file will run all examples.

set -ex
mvn clean compile

JAVA="java -cp target/classes -Xmx512M"


$JAVA jdd.examples.Simple1
$JAVA jdd.examples.Simple2
$JAVA jdd.examples.Simple3
$JAVA jdd.examples.ConfigExample
$JAVA jdd.examples.VariableOrder
$JAVA jdd.examples.Fibonacci 5

$JAVA jdd.examples.Adder 16
$JAVA jdd.examples.Milner 16
$JAVA jdd.examples.BDDQueens 7
$JAVA jdd.examples.ZDDQueens 7
$JAVA jdd.examples.ZDDCSPQueens 7

# This will take some too long time to run?
$JAVA jdd.examples.Solitaire