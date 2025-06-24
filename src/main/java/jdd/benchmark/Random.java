package jdd.benchmark;


import jdd.util.*;
import jdd.util.math.*;

// helper class for generating random in a runnable
class RunnableRandom implements Runnable {
    private final int type;
    private final int count;
    private final java.util.Random rnd;

    public RunnableRandom(int type, int count) {
        this.type = type;
        this.count = count;
        this.rnd = new java.util.Random();
    }

    public void run() {
        final int n = this.count;
        int sum = 0;
        switch (type) {
            case 0:
                for (int i = 0; i < n; i++) {
                    sum += (FastRandom.mtrand() % 1000);
                }
                break;
            case 1:
                for (int i = 0; i < n; i++) {
                    sum += rnd.nextInt(1000);
                }
                break;
        }

        // sum is here to stop code from being optimized out, but we need to use it:
        if (sum == 0xABBA) {
            System.out.println("Well, what are the chance of that happening...");
        }
    }
}

/**
 * + * Random generation speed
 * +
 */
public class Random {
    public static void run() {
        final int COUNT = 1000000;
        final int ROUNDS = 20;

        Benchmarking[] benchmarks = new Benchmarking[]{
                new Benchmarking("MT random", new RunnableRandom(0, COUNT), ROUNDS),
                new Benchmarking("JRE random", new RunnableRandom(1, COUNT), ROUNDS)
        };
        Benchmarking.process(benchmarks);
    }

    public static void main(String[] args) {
        run();
    }
}