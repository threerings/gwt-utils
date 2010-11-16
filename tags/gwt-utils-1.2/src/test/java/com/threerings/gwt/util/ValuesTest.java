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

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests the {@link Values} class.
 */
public class ValuesTest
{
    @Test public void testAnd ()
    {
        Value<Boolean> a = Value.create(false);
        Value<Boolean> b = Value.create(false);
        Value<Boolean> c = Value.create(false);

        @SuppressWarnings("unchecked") // sigh
        Value<Boolean> and = Values.and(a, b, c);
        assertFalse(and.get());

        a.update(true);
        assertFalse(and.get());
        b.update(true);
        assertFalse(and.get());
        c.update(true);
        assertTrue(and.get());
        c.update(false);
        assertFalse(and.get());
    }

    @Test public void testOr ()
    {
        Value<Boolean> a = Value.create(true);
        Value<Boolean> b = Value.create(true);
        Value<Boolean> c = Value.create(true);

        @SuppressWarnings("unchecked") // sigh
        Value<Boolean> or = Values.or(a, b, c);
        assertTrue(or.get());

        a.update(false);
        assertTrue(or.get());
        b.update(false);
        assertTrue(or.get());
        c.update(false);
        assertFalse(or.get());
        c.update(true);
        assertTrue(or.get());
    }
}
