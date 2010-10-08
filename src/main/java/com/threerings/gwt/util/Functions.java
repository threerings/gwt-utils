//
// $Id$

package com.threerings.gwt.util;

import java.util.Map;
import java.util.Set;

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

    /** Views the supplied map as a function from keys to values. */
    public static <K, V> Function<K, V> asFunc (final Map<K, V> map) {
        return new Function<K, V>() {
            public V apply (K key) {
                return map.get(key);
            }
        };
    }

    /** Views the supplied set as a function from elements to booleans. */
    public static <T> Function<K, Boolean> asFunc (final Set<T> set) {
        return new Function<T, Boolean>() {
            public Boolean apply (T key) {
                return set.contains(key);
            }
        };
    }
}
