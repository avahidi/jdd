package jdd.benchmark;

import jdd.util.*;

// helper class for copying in a runnable
class RunnableIntArrayCopy implements Runnable {
    private int type, count;
    private int[] a, b;

    public RunnableIntArrayCopy(int type, int count) {
        this.type = type;
        this.count = count;
        this.a = new int[count];
        this.b = new int[count];
        for (int i = 0; i < count; ++i) {
            this.a[i] = i;
            this.b[i] = -i;
        }
    }

    public void run() {
        switch (type) {
            case 0:
                jdd.util.Array.copy(this.a, this.b, this.b.length, 0, 0);
                break;
            case 1:
                System.arraycopy(this.a, 0, this.b, 0, this.a.length);
                break;
        }

        // this is needed to stop optimization:
        if (a.length == 12345 && a[0] == 0xAB && b[0] == 0xBA) {
            System.out.println("Well, what are the chance of that happening...");
        }
    }
}


/**
 * Array operations benchmark
 */
public class Array {
    public static void run() {
        final int ROUNDS = 100;
        Benchmarking benchmarks[] = new Benchmarking[]{
                new Benchmarking("int copy-10", new RunnableIntArrayCopy(0, 10), ROUNDS),
                new Benchmarking("int copy-system-10", new RunnableIntArrayCopy(1, 10), ROUNDS),
                new Benchmarking("int copy-1k", new RunnableIntArrayCopy(0, 1000), ROUNDS),
                new Benchmarking("int copy-system-1k", new RunnableIntArrayCopy(1, 1000), ROUNDS),
                new Benchmarking("int copy-10M", new RunnableIntArrayCopy(0, 10000000), ROUNDS),
                new Benchmarking("int copy-system-10M", new RunnableIntArrayCopy(1, 10000000), ROUNDS),
        };

        Benchmarking.process(benchmarks);
    }

    public static void main(String args[]) {
        run();
    }
}


