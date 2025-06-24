package jdd.benchmark;

import jdd.bdd.debug.*;
import jdd.util.*;

// Helper class to make queens a runable
class RunnableTrace implements Runnable {
    private final String filename;

    public RunnableTrace(String filename) {
        this.filename = filename;
    }

    public void run() {
        new BDDTraceSuite(filename, -1);
    }
}

/**
 * Trace benchmarker
 */
public class Trace {
    public static void main(String[] args) {
        Assert.notEquals(0, args.length, "No trace files were given");

        final int COUNT = 3;
        Benchmarking[] benchmarks = new Benchmarking[args.length];
        for (int i = 0; i < args.length; i++) {
            benchmarks[i] = new Benchmarking(args[i], new RunnableTrace(args[i]), COUNT);
        }

        Benchmarking.process(benchmarks);
    }
}