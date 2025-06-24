package jdd.util.math;

import jdd.util.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestPrime {


    private static boolean dumb_prime_check(int n) {
        int n0 = (int) Math.sqrt(n);
        if (n == 0) return false;
        if (n == 1) return true;
        for (int i = 2; i <= n0; i++) if ((n % i) == 0) return false;
        return true;
    }

    private static int dumb_next_prime(int n) {
        for (; ; )
            if (dumb_prime_check(n)) return n;
            else n++;
    }

    @Test
    public void testSimple() {
        assertTrue("1 is prime", Prime.isPrime(1));
        assertTrue("2 is prime", Prime.isPrime(2));
        assertTrue("3 is prime", Prime.isPrime(3));
        assertFalse("4 is NOT prime", Prime.isPrime(4));
        assertTrue("5 is prime", Prime.isPrime(5));
        assertFalse("6 is NOT prime", Prime.isPrime(6));
        assertTrue("7 is prime", Prime.isPrime(7));
        assertFalse("8 is NOT prime", Prime.isPrime(8));
        assertFalse("256 is NOT prime", Prime.isPrime(256));
        assertFalse("13221 is NOTprime", Prime.isPrime(13221));
    }

    @Test
    public void testRandom() {
        boolean failed = false;
        for (int i = 0; !failed && i < 3000; i++) {
            int n = (int) (Math.random() * 1234567);
            if (Prime.isPrime(n) != dumb_prime_check(n))
                failed = true;
        }
        assertFalse("Prime.isPrime failed", failed);
    }

    @Test
    public void testRandomNext() {
        boolean failed = false;
        for (int i = 0; !failed && i < 3000; i++) {
            int n = (int) (Math.random() * 1234567);
            if (Prime.nextPrime(n) != dumb_next_prime(n))
                failed = true;
        }
        assertFalse("nextPrime failed", failed);
    }
}