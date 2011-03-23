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

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * Displays default text in a text box or area and clears that text out when the box is focused.
 */
@Deprecated
public class DefaultTextListener
    implements FocusHandler, BlurHandler
{
    /**
     * Configures the target text box to display the supplied default text when it does not have
     * focus and to clear it out when the user selects it to enter text.
     * @deprecated use Widgets.setPlaceholderText(TextBoxBase, String)
     */
    @Deprecated
    public static void configure (TextBoxBase target, String defaultText)
    {
        DefaultTextListener listener = new DefaultTextListener(target, defaultText);
        target.addFocusHandler(listener);
        target.addBlurHandler(listener);
    }

    /**
     * Returns the contents of the supplied text box, accounting for the supplied default text.
     * @deprecated use Widgets.getText(TextBoxBase, String)
     */
    @Deprecated
    public static String getText (TextBoxBase target, String defaultText)
    {
        String text = target.getText().trim();
        return text.equals(defaultText.trim()) ? "" : text;
    }

    // from interface FocusHandler
    public void onFocus (FocusEvent event)
    {
        if (_target.getText().equals(_defaultText)) {
            _target.setText("");
        }
    }

    // from interface BlurHandler
    public void onBlur (BlurEvent event)
    {
        if (_target.getText().trim().equals("")) {
            _target.setText(_defaultText);
        }
    }

    protected DefaultTextListener (TextBoxBase target, String defaultText)
    {
        _target = target;
        _defaultText = defaultText;
        if (_target.getText().trim().equals("")) {
            _target.setText(defaultText);
        }
    }

    protected TextBoxBase _target;
    protected String _defaultText;
}
