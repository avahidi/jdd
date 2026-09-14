#!/bin/sh

mvn clean compile

echo "Recording execution time with mesa. This will take some time, go get some coffee..."
echo "See https://github.com/avahidi/mesa"

TAG=`git log -1 --oneline || echo "No git?"`
NOTE=${NOTE:-"$TAG"}
MESA="mesa --filter=exact"
JAVA="java -cp target/classes -Xmx512M"

#
$MESA --note="$NOTE" --warmups=2 --runs=10 -- $JAVA jdd.examples.BDDQueens 11
$MESA --note="$NOTE" --warmups=2 --runs=10 -- $JAVA jdd.examples.ZDDQueens 12
$MESA --note="$NOTE" --warmups=2 --runs=10 -- $JAVA jdd.examples.ZDDCSPQueens 13

$MESA --note="$NOTE" --warmups=1 --runs=5 -- $JAVA jdd.examples.Adder 1024
$MESA --note="$NOTE" --warmups=1 --runs=2 -- $JAVA jdd.bdd.debug.BDDTraceSuite data/yangs_traces.zip 10240
