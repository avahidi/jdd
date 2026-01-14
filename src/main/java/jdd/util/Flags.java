
package jdd.util;


/**
 * abstraction of flags.
 * <p>
 * The Flag object allows you to store and access 32 distinct flags
 */

public class Flags {
    private int flags;

    /**
     * create a Flag object with the given value
     */

    public Flags(int f) {
        this.flags = f;
    }

    /**
     * create an empty Flag object
     */
    public Flags() {
        this(0);
    }

    // --------------------------------

    /**
     * set the 32-bit flags variable in one operation
     */
    protected void setAll(int f) {
        flags = f;
    }

    /**
     * get all the 32 flags variable in one operation
     */
    public int getAll() {
        return flags;
    }

    /**
     * copy FROM the {@code f} object
     */
    public void copyFlags(final Flags f) {
        this.flags = f.flags;
    }

    // --------------------------------

    /**
     * set the flag {@code flag}
     */
    private void set(int flag) {
        flags |= (1 << flag);
    }

    /**
     * clear/reset the flag {@code flag}
     */
    private void reset(int flag) {
        flags &= ~(1 << flag);
    }

    /**
     * set the flag {@code flag} to {@code set}
     */
    public void set(int f, boolean set) {
        if (set) set(f);
        else reset(f);
    }

    /**
     * get the value of {@code flag}
     */
    public boolean get(int flag) {
        return (flags & (1 << flag)) != 0;
    }
}