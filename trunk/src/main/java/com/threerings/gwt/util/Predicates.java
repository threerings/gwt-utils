//
// $Id$

package com.threerings.gwt.util;

import java.util.Collection;

import com.google.common.base.Predicate;

/**
 * A collection of general purpose predicates. To be replaced by Google Collections when same is
 * usable via GWT.
 */
public class Predicates
{
    /**
     * Returns a predicate that evaluates to true if the object reference being tested is a member
     * of the given collection.
     */
    public <T> Predicate<T> in (final Collection<? extends T> target)
    {
        return new Predicate<T>() {
            public boolean apply (T arg) {
                try {
                    return target.contains(arg);
                } catch (NullPointerException e) {
                    return false;
                } catch (ClassCastException e) {
                    return false;
                }
            }
        };
    }
}
