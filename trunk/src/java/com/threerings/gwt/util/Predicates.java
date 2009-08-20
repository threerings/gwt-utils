//
// $Id$

package com.threerings.gwt.util;

import com.google.common.base.Predicate;

/**
 * A collection of general purpose predicates.
 */
public class Predicates
{
    /** A predicate that always returns true. */
    public static class TRUE<O> implements Predicate<O> {
        public boolean apply (O o) {
            return true;
        }
    }

    /** A predicate that always returns false. */
    public static class FALSE<O> implements Predicate<O> {
        public boolean apply (O o) {
            return false;
        }
    }
}
