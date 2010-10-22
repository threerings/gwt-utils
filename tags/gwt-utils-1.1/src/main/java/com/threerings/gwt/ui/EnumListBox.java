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

package com.threerings.gwt.ui;

import java.util.EnumSet;

import com.google.gwt.user.client.ui.ListBox;

/**
 * A {@link ListBox} that contains values of an enum. Clients can override {@link #toLabel} to
 * display text other than the string value of the enum in the box.
 */
public class EnumListBox<E extends Enum<E>> extends ListBox
{
    /**
     * Creates an enum list box that displays all values in the supplied enum.
     */
    public EnumListBox (Class<E> eclass)
    {
        this(eclass, EnumSet.allOf(eclass));
    }

    /**
     * Creates an enum list box that displays the values in the supplied set.
     */
    public EnumListBox (Class<E> eclass, EnumSet<E> elements)
    {
        _eclass = eclass;
        for (E value : elements) {
            addItem(toLabel(value), value.toString());
        }
    }

    /**
     * Selects the specified value.
     */
    public void setSelectedValue (E value)
    {
        String valstr = value.toString();
        for (int ii = 0; ii < getItemCount(); ii++) {
            if (getValue(ii).equals(valstr)) {
                setSelectedIndex(ii);
                break;
            }
        }
    }

    /**
     * Returns the currently selected value, or null if no value is selected.
     */
    public E getSelectedValue ()
    {
        int selidx = getSelectedIndex();
        return (selidx < 0) ? null : Enum.valueOf(_eclass, getValue(selidx));
    }

    /**
     * Returns a string that will be shown to the user for the specified enum.
     */
    protected String toLabel (E value)
    {
        return value.toString();
    }

    protected Class<E> _eclass;
}
