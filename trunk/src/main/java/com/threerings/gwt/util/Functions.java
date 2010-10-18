//
// $Id$
//
// OOO GWT Utils - utilities for creating GWT applications
// Copyright (C) 2009-2010 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/ooo-gwt-utils/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

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
