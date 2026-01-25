
package jdd.bdd;

import java.util.BitSet;

import jdd.util.*;
import jdd.util.math.*;

/**
 * Various BDD helper functions.
 * Some of these used to live inside BDD.java but have been moved here to simplify things.
 */

public final class BDDHelper {

    /**
     * Values for variable assignments and strategies, used for example in {@link #oneSat}.
     * @see #oneSat
     */
    public static final int
        ONE = 1,
        ZERO = 0,
        DONT_CARE = -1,
        SELECT_RANDOMLY = 2
        ;

    /**
     * oneSatBDD returns a single tree path (itself as a bdd) that satisfies a bdd.
     * In this version some variables are skipped as don't care
     * @param mgr the BDD manager
     * @param bdd the BDD to satisfy, must not be 0.
     * @return bdd assignments that satisfy bdd. Contains 0, 1 and -1 (don't care) for each variable
     */
    public final static int oneSatBDD(BDD mgr, int bdd) {
        if (bdd < 2) return bdd;

        int var = mgr.getVar(bdd);
        if (mgr.getLow(bdd) == 0) {
            int high = mgr.ref(oneSatBDD(mgr, mgr.getHigh(bdd)));
            int u = mgr.mk(var, 0, high);
            mgr.deref(high);
            return u;
        } else {
            int low = mgr.ref(oneSatBDD(mgr, mgr.getLow(bdd)));
            int u = mgr.mk(var, low, 0);
            mgr.deref(low);
            return u;
        }
    }


    /**
     * oneSatBitSet returns one set of variable assignments that satisfies a bdd.
     * A strategy is used to handle don't-care assignments, i.e. where either 0 or 1 would work.
     *
     * @param mgr the BDD manager
     * @param bdd the BDD to satisfy, must not be 0.
     * @param buffer optional buffer to use for the result. If null  a new buffer to be allocated
     * @param strategy decides how to assign don't-care variables
     * @return variable assignments that satisfy bdd. Contains 0, 1 and -1 (don't care) for each variable
     */
    public static final BitSet oneSatBitSet(BDD mgr, int bdd, int[] buffer, int strategy) {
        int [] vars = oneSat(mgr, bdd, buffer, strategy);
        BitSet bs = new BitSet(vars.length);
        for(int i = 0; i < vars.length; i++)
            bs.set(i, vars[i] == 1);
        return bs;
    }


    /**
     * oneSat returns one set of variable assignments that satisfies a bdd.
     * A strategy is used to handle don't-care assignments, i.e. where either 0 or 1 would work.
     *
     * @param mgr the BDD manager
     * @param bdd the BDD to satisfy, must not be 0.
     * @param buffer optional buffer to use for the result. If null  a new buffer to be allocated
     * @param strategy decides how to assign don't-care variables
     * @return variable assignments that satisfy bdd. Contains 0, 1 and -1 (don't care) for each variable
     */
    public static final int[] oneSat(BDD mgr, int bdd, int[] buffer, int strategy) {
        if (buffer == null) {
            int n = mgr.numberOfVariables() ;
            buffer = new int[n];
        }

        if(strategy == SELECT_RANDOMLY  ) {
            for(int i = 0; i < buffer.length; i++) buffer[i] = FastRandom.mtrand() & 1;
        } else {
            Array.set(buffer, strategy);
        }
        oneSat_rec(mgr, buffer, bdd);
        return buffer;
    }

    private static final void oneSat_rec(BDD mgr, int[] buffer, int bdd) {
        if (bdd < 2) return;

        int var = mgr.getVar(bdd);
        if (mgr.getLow(bdd) == 0) {
            buffer[var] = 1;
            oneSat_rec(mgr, buffer, mgr.getHigh(bdd));
        } else {
            buffer[var] = 0;
            oneSat_rec(mgr, buffer, mgr.getLow(bdd));
        }
    }


    /**
     * encode a number as a BDD given a set of boolean variables
     * @param mgr the BDD manager
     * @param vars list of variables to use
     * @param num the number to encode
     * @return bdd encoding of number using these variables
     */
    public static final int encode(BDD mgr, int[] vars, int num) {
        int ret = 1;
        for (int i = 0; i < vars.length; i++) {
            int next = (num & (1L << i)) == 0 ? mgr.not(vars[i]) : vars[i];
            mgr.ref(next);
            ret = mgr.andTo(ret, next);
            mgr.deref(next);
        }
        return ret;
    }

    /**
     * encode a string as a BDD given a set of boolean variables.
     * In other words, return minterm for the selected variables and their assignments.
     * @param mgr the BDD manager
     * @param vars list of variables to use. If null all variables will be used
     * @param val to encode, for example "0011--0". Should have the same length as vars!
     * @return bdd encoding of number using these variables
     *
     */
    public static final int encode(BDD mgr, int[] vars, String val) {
        int ret = 1;
        if(vars == null) {
            vars = mgr.getAllVariables();
        }

        for (int i = 0; i < vars.length && i < val.length(); i++) {
            int next = 1;

            if(val.charAt(i) == '0') next = mgr.not(vars[i]);
            else if(val.charAt(i) == '1') next = vars[i];

            System.out.printf("%d: %c -> %d -> %d\n", i, val.charAt(i), vars[i], next);
            mgr.ref(next);
            ret = mgr.andTo(ret, next);
            mgr.deref(next);
        }
        return ret;
    }

    /**
     * encodeBooleans is similar to #encode but does not construct a BDD.
     * @param num the number to encode
     * @param length size of the number in bits
     * @param index where in the output should our number be inserted
     * @param output boolean vector where the encoded values are places
     */
    public static void encodeBooleans(int num, int length, int index, boolean[] output) {
        for (int i = 0; i < length; i++)
            output[index++] = ((num & (1L << i)) != 0);
    }
}

