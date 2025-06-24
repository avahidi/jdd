package jdd.util;

import jdd.util.math.*;

/**
 * Benchmarking of an operation, returns mean and std dev time
 * +
 */
public class Benchmarking {
    public static final String HEADER = "#        name       , warmup, rounds,\tmean,\tstddev";

    private final Runnable op;
    private final String name;
    private final int warmup;
    private final int count;
    private double mean, stddev;

    public Benchmarking(String name, Runnable op, int warmup, int count) {
        this.name = name;
        this.op = op;
        this.warmup = warmup;
        this.count = count;
    }

    public Benchmarking(String name, Runnable op, int count) {
        this(name, op, Math.max(1, count / 5), count);
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    public void run() {
        double[] values = new double[count];

        // warmup phase
        for (int i = 0; i < warmup; i++) {
            op.run();
        }

        // benchmark phase
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            op.run();
            long end = System.nanoTime();
            values[i] = ((double) end - (double) start) / 1000000.0f;
        }

        double[] moments = Statistics.moments(values);
        this.mean = moments[0];
        this.stddev = moments[2];
    }

    public String toString() {
        return String.format("%20s, %6d, %6d,\t%3.3f,\t%3.3f",
                name, warmup, count, this.mean, this.stddev);
    }

    /**
     * Run a list of benchmarks and output the results
     */
    public static void process(Benchmarking[] list) {
        System.out.printf("%s\n", HEADER);
        for (Benchmarking b : list) {
            b.run();
            System.out.printf("%s\n", b);
        }
    }
}