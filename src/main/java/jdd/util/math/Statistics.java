package jdd.util.math;


import jdd.util.*;

/**
 * Statistics stuff should go here
 */

public class Statistics {

    /**
     * Given an array, return normal distribution moments [mean, variance, std deviation]
     */
    public static double[] moments(double []data) {
        double mean = 0;
        for(double v: data) {
            mean += v;
        }
        mean /= data.length;

        double variance = 0;
        for(double v : data) {
            variance += Math.pow(v - mean, 2);
        }
        variance /= data.length;

        return new double[]{ mean, variance,  Math.sqrt(variance) };
    }

}
