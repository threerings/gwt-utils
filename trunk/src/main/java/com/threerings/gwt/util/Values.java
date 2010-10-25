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

import java.util.Arrays;

import com.threerings.gwt.util.Functions;

/**
 * {@link Value}-related utility methods.
 */
public class Values
{
    /**
     * Returns a value which is the logical NOT of the supplied value.
     */
    public static Value<Boolean> not (Value<Boolean> value)
    {
        return value.map(Functions.NOT);
    }

    /**
     * Returns a value which is the logical AND of the supplied values.
     */
    public static Value<Boolean> and (final Iterable<Value<Boolean>> values)
    {
        MultiMappedValue<Boolean, Boolean> mapped =
            new MultiMappedValue<Boolean, Boolean>(computeAnd(values)) {
            @Override protected Boolean recompute (Boolean ignored) {
                return computeAnd(values);
            }
        };
        for (Value<Boolean> value : values) {
            value.addListener(mapped);
        }
        return mapped;
    }

    /**
     * Returns a value which is the logical AND of the supplied values.
     */
    public static Value<Boolean> and (Value<Boolean>... values)
    {
        return and(Arrays.asList(values));
    }

    /**
     * Returns a value which is the logical OR of the supplied values.
     */
    public static Value<Boolean> or (final Iterable<Value<Boolean>> values)
    {
        MultiMappedValue<Boolean, Boolean> mapped =
            new MultiMappedValue<Boolean, Boolean>(computeOr(values)) {
            @Override protected Boolean recompute (Boolean ignored) {
                return computeOr(values);
            }
        };
        for (Value<Boolean> value : values) {
            value.addListener(mapped);
        }
        return mapped;
    }

    /**
     * Returns a value which is the logical OR of the supplied values.
     */
    public static Value<Boolean> or (Value<Boolean>... values)
    {
        return or(Arrays.asList(values));
    }

    // my kingdom for a higher order
    protected static boolean computeAnd (Iterable<Value<Boolean>> values)
    {
        boolean result = true;
        for (Value<Boolean> value : values) {
            result = result && value.get();
        }
        return result;
    }

    protected static boolean computeOr (Iterable<Value<Boolean>> values)
    {
        boolean result = false;
        for (Value<Boolean> value : values) {
            result = result || value.get();
        }
        return result;
    }

    /** Used by {@link #map}. */
    protected abstract static class MultiMappedValue<A, B> extends Value<B>
        implements Value.Listener<A>
    {
        protected MultiMappedValue (B current) {
            super(current);
        }

        @Override // from Value<B>
        public void update (B value) {
            throw new UnsupportedOperationException();
        }

        // from interface Value.Listener<A>
        public void valueChanged (A value) {
            super.update(recompute(value));
        }

        protected abstract B recompute (A trigger);
    }
}
