package jdd.benchmark;

import jdd.examples.*;
import jdd.util.*;

// Helper class to make queens a runable
class RunnableQueens implements Runnable {
    private int type, size;

    public RunnableQueens(int type, int size) {
        this.type = type;
        this.size = size;
    }

    public void run() {
        switch(type) {
            case 0:
                new BDDQueens(size);
                break;
            case 1:
                new ZDDQueens(size);
                break;
            case 2:
                new ZDDCSPQueens(size);
        }
    }
}

/**
 * Very basic Queens benchmark, used to find performance regressions
 */
public class Queens {
    public static void run() {
        final int COUNT = 10;
        Benchmarking benchmarks[] = new Benchmarking[] {
                new Benchmarking("BDD-11", new RunnableQueens(0, 11), 2, COUNT),
                new Benchmarking("ZDD-12", new RunnableQueens(1, 12), 2, COUNT),
                new Benchmarking("ZDDCSP-13", new RunnableQueens(2, 13), 2, COUNT)
        };
        Benchmarking.process(benchmarks);
    }

    public static void main(String args[]) {
        run();
    }
}
