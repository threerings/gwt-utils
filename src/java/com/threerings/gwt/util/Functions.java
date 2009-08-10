//
// $Id$

package com.threerings.gwt.util;

/**
 * A collection of general purpose functions.
 */
public class Functions
{
    /** Implements boolean not. */
    public static Function<Boolean, Boolean> NOT = new Function<Boolean, Boolean>() {
        public Boolean apply (Boolean value) {
            return !value;
        }
    };
}
