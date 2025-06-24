
package jdd.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestFlags {

    @Test
    public void testSet() {
        Flags f = new Flags();

        f.set(0, true);
        f.set(1, true);
        f.set(1, false);
        f.set(2, true);
        assertTrue("get (1)", f.get(0));
        assertFalse("get (2)", f.get(1));
        assertTrue("get (3)", f.get(2));
    }

    @Test
    public void testSetAll() {
        Flags f = new Flags();

        f.setAll(0);
        for (int i = 0; i < 32; i++)
            assertFalse("get FALSE (i)", f.get(i));

        f.setAll(-1);
        for (int i = 0; i < 32; i++)
            assertTrue("get TRUE (i)", f.get(i));

    }
}