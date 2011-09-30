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

import com.google.gwt.user.client.ui.Label;

import com.threerings.gwt.util.Value;

/**
 * Displays a dynamically changing value. Automatically handles regenerating its contents when the
 * value changes and registers and clears its listenership when it is added to and removed from the
 * DOM.
 */
public class ValueLabel<T> extends Label
    implements Value.Listener<T>
{
    /**
     * Creates a value label for the supplied value with the specified CSS styles.
     */
    public static <T> ValueLabel<T> create (Value<T> value, String... styles)
    {
        return new ValueLabel<T>(value, styles);
    }

    /**
     * Creates a value label with the supplied value and CSS styles.
     */
    public ValueLabel (Value<T> value, String... styles)
    {
        _value = value;
        Widgets.setStyleNames(this, styles);
    }

    // from Value.Listener<T>
    public void valueChanged (T value)
    {
        setText(getText(value));
    }

    @Override // from Widget
    public void onLoad ()
    {
        super.onLoad();
        _value.addListener(this);
        valueChanged(_value.get());
    }

    @Override // from Widget
    public void onUnload ()
    {
        super.onLoad();
        _value.removeListener(this);
    }

    /**
     * Called to generate our text when the value changes. The default implementation simply
     * converts the value to a string via {@link String#valueOf}.
     */
    protected String getText (T value)
    {
        return String.valueOf(value);
    }

    /** The value we're displaying. */
    protected Value<T> _value;
}
