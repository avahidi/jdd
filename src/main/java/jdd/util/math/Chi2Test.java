
package jdd.util.math;

import jdd.util.*;

/**
 * \chi^2 random distribution test.
 *
 * <p> This class is used to test the distribution of series
 * of numbers such hashes and (pseudo) random numbers.
 *
 *
 * <p>
 * assume that you want to test a function f() which returns a number between 0 and N-1:
 * <pre>
 * Chi2Test  c2t = new Chi2Test(N);
 *
 * while(c2t.more()) c2t.add( f() );
 *
 * if(c2t.isChi2Acceptable())
 *    System.out.println("The distribution of f() is random enough for me!");
 * <pre>
 */

public class Chi2Test {

    private final int n;
    private final int samples_needed;
    private int samples_have;
    private final int[] bins;
    private boolean has_chi2;    //have we computed the values?
    private double the_chi2, the_stddev; // when computed, the values are stored here

    /**
     * start a chi^2 for the input numbers 0..n-1
     *
     * <p> <tt>n</tt> must be larger than 20. don't make it too large unless you
     * have enough memory for it.
     *
     * <p> Also, if <tt>n</tt> is too small (say bellow 1000), then you might get
     * many false answers so instead consider the majority of multiple runs.
     */
    public Chi2Test(int n) {
        // Test.check(n > 20, "n to small");
        this.n = n;
        this.samples_needed = 25 * n + 3; // Knuth said something about 5*n, but what does he knows?
        this.bins = new int[n];

        reset();
    }

    /**
     * reset the chi^2, start all over
     */
    public void reset() {
        samples_have = 0;
        Array.set(bins, 0);
        has_chi2 = false;
    }

    /**
     * returns true if it has enough samples to give an accurate answer.
     *
     * <p>NOTE: most other functions here cannot be called before this functions
     * starts returning <tt>false</tt>es!
     */

    public boolean more() {
        return samples_have < samples_needed;
    }

    /**
     * add a new number. you now you have fed it enough numbers when more() returns false.
     *
     * @see #more
     */

    public void add(int x) {
        bins[x]++;
        samples_have++;
        has_chi2 = false; // the old chi^2 is no longer valid
    }


    /**
     * get the chi2 value. do not call before more() has returned true!
     */
    public double getChi2() {
        if (!has_chi2) computeChi2();
        return the_chi2;
    }

    /**
     * get the standard deviation. do not call before more() has returned true!
     */
    public double getStdDev() {
        if (!has_chi2) computeChi2(); // std-dev is computed in the same function as chi^2
        return the_stddev;
    }

    /**
     * chi^2 is computed here
     */
    private void computeChi2() {
        double expected = samples_have / (double) n;
        // compute chi2 = sum_i (observed_i - expected_i) ^2 / expected_i
        the_chi2 = 0;
        for (int i = 0; i < n; i++) {
            double t = bins[i] - expected;
            the_chi2 += t * t;
        }
        the_chi2 /= expected;
        the_stddev = (the_chi2 - n) / Math.sqrt(n);
        has_chi2 = true;

    }

    // ------------------------------------------------------------------

    /**
     * "Acceptable" does not mean good. For example, for hash functions,
     * acceptable means very good. So don't take it as a hard limit, it
     * sometimes fails so do multiple runs,
     *
     * @return true if the current chi2 value is acceptable
     * @see #isStdDevAcceptable
     * @see #getChi2
     */
    public boolean isChi2Acceptable() {
        double c2 = getChi2();
        return Math.abs(c2 - n) < (3.5 * Math.sqrt(n)); // should really be 3.0
    }

    /**
     * "Acceptable" does not mean good. For example, for hash functions,
     * acceptable means very good. So don't take it as a hard limit, it
     * sometimes fails so do multiple runs,
     *
     * @return true if the current standard deviation is acceptable.
     * @see #isChi2Acceptable
     * @see #getStdDev
     */
    public boolean isStdDevAcceptable() {
        double stddev = getStdDev();
        return Math.abs(stddev) < 3.5; // should actually be 3.0
    }

    /**
     * Get the distribution
     */
    public int[] getBins() {
        return bins;
    }
    // ------------------------------------------------------------------

    public static void main(String[] args) {
        // THIS IS NOT A TEST!
        // this is just to watch the random number generators...
        int max = Prime.nextPrime(1000);

        Chi2Test c1 = new Chi2Test(max);
        while (c1.more()) c1.add((int) (Math.random() * max));

        Chi2Test c2 = new Chi2Test(max);
        java.util.Random rnd = new java.util.Random();
        while (c2.more()) c2.add(rnd.nextInt(max));

        Chi2Test c3 = new Chi2Test(max);
        while (c3.more()) c3.add(FastRandom.mtrand() % max);

        System.out.printf("Math.random      : chi^2=%.3f\tstdev=%.3f\n", c1.getChi2(), c1.getStdDev());
        System.out.printf("Random.NextInt   : chi^2=%.3f\tstdev=%.3f\n", c2.getChi2(), c2.getStdDev());
        System.out.printf("FastRandom.mtrand: chi^2=%.3f\tstdev=%.3f\n", c3.getChi2(), c3.getStdDev());

    }
}