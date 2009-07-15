//
// $Id$

package com.threerings.gwt.util;

/**
 * Represents a function from A to B.
 */
public interface Function<A, B>
{
    /**
     * Applies the function to the supplied value.
     */
    public B apply (A value);
}
