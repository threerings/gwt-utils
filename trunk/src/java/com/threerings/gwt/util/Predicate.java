//
// $Id$

package com.threerings.gwt.util;

/**
 * A single argument predicate function.
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
