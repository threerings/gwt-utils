//
// $Id$

package com.threerings.gwt.util;

/**
 * A single argument predicate function.
 *
 * TODO: google-collect is supposed to work with GWT, especially simple fucking shit like
 * a stupid fucking INTERFACE, but nobody seems to yet be building a GWT-compatible jar and
 * I tried for a while, but ran up against the problems with needing the source in the same jar
 * for the standard Java annotations. And now I have a tension headache.
 *
 * This note is here to remind you not to try this unless you really know what you're doing,
 * or they actually distribute a GWT module for adapting the google collect code.
 * Actually, I'm sure I could just copy the code in here, and get it working that way,
 * but that's ugly and not easily maintainable.
 */
public interface Predicate<T>
{
    public static class TRUE<O> implements Predicate<O> {
        public boolean apply (O o) {
            return true;
        }
    }

    public static class FALSE<O> implements Predicate<O> {
        public boolean apply (O o) {
            return false;
        }
    }

    boolean apply (T o);
}
