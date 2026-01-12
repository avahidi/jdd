package jdd.util.math;


import jdd.util.*;

/**
 * Statistics stuff should go here
 */

public class Statistics {

    /**
     * Given an array, return normal distribution moments [mean, variance, std deviation]
     */
    public static double[] moments(double[] data) {
        double mean = 0;
        for (double v : data) {
            mean += v;
        }
        mean /= data.length;

        double variance = 0;
        for (double v : data) {
            variance += Math.pow(v - mean, 2);
        }
        variance /= data.length;

        return new double[]{mean, variance, Math.sqrt(variance)};
    }

    /**
     * Compute Pearson's chi-squared statistic given observed frequencies.
     *
     * @param observed array of observed frequencies in each bin
     * @param totalSamples total number of samples
     * @return chi-squared value using formula: sum_i ((observed_i - expected_i)^2 / expected_i)
     */
    public static double chiSquared(int[] observed, int totalSamples) {
        int numBins = observed.length;
        double expected = totalSamples / (double) numBins;
        double chi2 = 0;
        for (int count : observed) {
            double diff = count - expected;
            chi2 += (diff * diff) / expected;
        }
        return chi2;
    }

}