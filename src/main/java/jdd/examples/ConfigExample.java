
package jdd.examples;

import jdd.util.*;


/**
 * This examples shows how the configuration of the BDD package can
 * affect the running-time performance of an application.
 * <p>
 * We will time the Adder example under different configurations.
 * The result will hopefully convince you that for good performance,
 * you must tune the BDD package for the specific type of problem you
 * are working with.
 *
 * @see jdd.util.Configuration
 * @see Adder
 */


public class ConfigExample {
    /**
     * the size of our Adder
     */
    private static final int N = 256;


    /**
     * build the adder once and print its memory and time consumption
     */
    private static void test() {
        long time = System.currentTimeMillis();
        Adder adder = new Adder(N);
        long memory = adder.getMemoryUsage() / 1024;
        time = System.currentTimeMillis() - time;

        // REMOVE this line if you getting too much information :)
        adder.showStats();

        adder.cleanup();


        JDDConsole.out.printf("**** TIME = %dms , MEMORY = %dKB ****\n\n", time, memory);
    }

    public static void main(String[] args) {


        JDDConsole.out.printf("ConfigExample.java:\n");
        JDDConsole.out.printf("We will now profile Adder(%d) under different configurations\n", N);


        // NOTE:
        // every time you change something, make sure to change it back when you are done!

        JDDConsole.out.printf("\nDefault configuration\n");
        test();

        JDDConsole.out.printf("\nSmaller OP cache\n");
        Configuration.bddOpcacheDiv = 8;
        test();
        Configuration.bddOpcacheDiv = Configuration.DEFAULT_BDD_OPCACHE_DIV;


        JDDConsole.out.printf("\nToo small OP cache\n");
        Configuration.bddOpcacheDiv = 1000;
        test();
        Configuration.bddOpcacheDiv = Configuration.DEFAULT_BDD_OPCACHE_DIV;

        JDDConsole.out.printf("\nFaster nodetable grow:\n");
        Configuration.nodetableGrowMin = Configuration.nodetableGrowMax = 500000;
        test();
        Configuration.nodetableGrowMin = Configuration.DEFAULT_NODETABLE_GROW_MIN;
        Configuration.nodetableGrowMax = Configuration.DEFAULT_NODETABLE_GROW_MAX;


        JDDConsole.out.printf("\nComputation caches are NOT allowed to grow:\n");
        Configuration.maxSimplecacheGrows = 0;
        test();
        Configuration.maxSimplecacheGrows = Configuration.DEFAULT_MAX_SIMPLECACHE_GROWS;


        JDDConsole.out.printf("\nComputation caches are allowed to grow, but only under very high hitrate:\n");
        Configuration.minSimplecacheHitrateToGrow = 85;
        test();
        Configuration.minSimplecacheHitrateToGrow = Configuration.DEFAULT_MIN_SIMPLECACHE_HITRATE_TO_GROW;


        // we are done
        JDDConsole.out.printf("\n\nThe results wasn't what you were expecting huh?\n");
        JDDConsole.out.printf("Hope this example has learned you the importance of BDD tuning!\n");
        JDDConsole.out.printf("\n");

    }
}