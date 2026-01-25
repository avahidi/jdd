package jdd.bdd;

import jdd.util.*;
import jdd.util.math.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.BitSet;

public class TestBDDHelper {

    @Test
    public void testOneSat() {
        BDD jdd = new BDD(200);
        jdd.createVars(3);

        int val = BDDHelper.encode(jdd, null, "0-1");

        int [] sat0 = BDDHelper.oneSat(jdd, val, null, BDDHelper.ZERO);
        int [] expected0 = {0, 0, 1};
        assertTrue("oneSatFull(0) of 0-1 is 001", Arrays.equals( sat0, expected0));

        int [] sat1 = BDDHelper.oneSat(jdd, val, null, BDDHelper.ONE);
        int [] expected1 = {0, 1, 1};
        assertTrue("oneSatFull(1) of 0-1 is 011", Arrays.equals(sat1,  expected1));

        int [] satr = BDDHelper.oneSat(jdd, val, null, BDDHelper.SELECT_RANDOMLY);
        assertTrue("oneSatFull(random) of 0-1 is 011 or 001",
            Arrays.equals( satr, expected0) || Arrays.equals( satr, expected1));

        int [] sat = BDDHelper.oneSat(jdd, val, null, BDDHelper.DONT_CARE);
        int [] expected = {0, -1, 1};
        assertTrue("oneSatFull(dontcare) of 0-1 is 0-1", Arrays.equals( sat, expected));
    }

    @Test
    public void testOneSatBDD() {
        BDD jdd = new BDD(200);
        int v1 = jdd.createVar();
        int v2 = jdd.createVar();
        int v3 = jdd.createVar();

        int val1 = BDDHelper.encode(jdd, null, "111");
        int val2 = BDDHelper.encode(jdd, null, "0-1");

        int sat1 = BDDHelper.oneSatBDD(jdd, val1);
        assertTrue("full minterm is its own sat", val1 == sat1);

        int sat2 =BDDHelper.oneSatBDD(jdd, val2);
        int tmp = jdd.ref( jdd.not(v1));
        tmp = jdd.andTo(tmp, v3);
        assertTrue("0-1 is part of 0-1 sat", jdd.and(sat2, tmp) == sat2);

        int val3 = jdd.ref( jdd.or(val1, val2));
        int sat3 = BDDHelper.oneSatBDD(jdd, val3);
        assertTrue("--1 is part of (0-1 OR 111) sat", jdd.and(sat3, v3) == sat3);
    }

    @Test
    public void testOneBitset() {
        BDD jdd = new BDD(200);
        jdd.createVars(3);

        int val = BDDHelper.encode(jdd, null, "0-1");

        BitSet bs0 = BDDHelper.oneSatBitSet(jdd, val, null, BDDHelper.ZERO);
        assertTrue("bitset test strategy=ZERO",
            bs0.get(0) == false && bs0.get(1) == false && bs0.get(2) == true);

       BitSet bs1 = BDDHelper.oneSatBitSet(jdd, val, null, BDDHelper.ONE);
        assertTrue("bitset test strategy=ONE",
            bs1.get(0) == false && bs1.get(1) == true && bs1.get(2) == true);
    }
}