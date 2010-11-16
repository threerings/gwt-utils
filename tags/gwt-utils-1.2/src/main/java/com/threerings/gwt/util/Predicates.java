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
