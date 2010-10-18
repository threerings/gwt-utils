//
// $Id$

package com.threerings.gwt.util;

import java.util.Map;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * A collection of general purpose functions. To be replaced by Google Collections when same is
 * usable via GWT.
 */
public class Functions
{
    /** Implements boolean not. */
    public static Function<Boolean, Boolean> NOT = new Function<Boolean, Boolean>() {
        public Boolean apply (Boolean value) {
            return !value;
        }
    };

    /**
     * Returns a function which performs a map lookup with a default value. The function created by
     * this method returns defaultValue for all inputs that do not belong to the map's key set.
     */
    public static <K, V> Function<K, V> forMap (final Map<K, ? extends V> map, final V defaultValue)
    {
        return new Function<K, V>() {
            public V apply (K key) {
                V value = map.get(key);
                return (value != null || map.containsKey(key)) ? value : defaultValue;
            }
        };
    }

    /**
     * Returns a function that returns the same boolean output as the given predicate for all
     * inputs.
     */
    public static <T> Function<T, Boolean> forPredicate (final Predicate<T> predicate)
    {
        return new Function<T, Boolean>() {
            public Boolean apply (T arg) {
                return predicate.apply(arg);
            }
        };
    }
}
