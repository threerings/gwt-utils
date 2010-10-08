//
// $Id$

package com.threerings.gwt.util;

import java.util.Map;

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
    public static <K, V> Function<K, V> fromMap (final Map<K, V> map) {
        return new Function<K, V>() {
            public V apply (K key) {
                return map.get(key);
            }
        };
    }
}
