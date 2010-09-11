//
// $Id$

package com.threerings.gwt.util;

import com.google.common.base.Function;

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
