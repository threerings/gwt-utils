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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.util.Value;

/**
 * Handles the binding of various widget states to dynamic {@link Value}s.
 */
public class Bindings
{
    /**
     * Binds the enabledness state of the target widget to the supplied boolean value.
     */
    public static void bindEnabled (Value<Boolean> value, final FocusWidget... targets)
    {
        value.addListener(new Value.Listener<Boolean>() {
            public void valueChanged (Boolean enabled) {
                for (FocusWidget target : targets) {
                    target.setEnabled(enabled);
                }
            }
        });
        for (FocusWidget target : targets) {
            target.setEnabled(value.get());
        }
    }

    /**
     * Binds the visible state of the target widget to the supplied boolean value.
     */
    public static void bindVisible (Value<Boolean> value, final Widget... targets)
    {
        value.addListener(new Value.Listener<Boolean>() {
            public void valueChanged (Boolean visible) {
                for (Widget target : targets) {
                    target.setVisible(visible);
                }
            }
        });
        for (Widget target : targets) {
            target.setVisible(value.get());
        }
    }

    /**
     * Binds the specified toggle button to the supplied boolean value. The binding will work both
     * ways: interactive changes to the toggle button will update the value and changes to the
     * value will update the state of the toggle button.
     */
    public static void bindDown (final ToggleButton toggle, final Value<Boolean> value)
    {
        toggle.addClickHandler(new ClickHandler() {
            public void onClick (ClickEvent event) {
                value.update(toggle.isDown());
            }
        });
        value.addListenerAndTrigger(new Value.Listener<Boolean>() {
            public void valueChanged (Boolean value) {
                toggle.setDown(value);
            }
        });
    }

    /**
     * Returns a click handler that toggles the supplied boolean value when clicked.
     */
    public static ClickHandler makeToggler (final Value<Boolean> value)
    {
        return new ClickHandler() {
            public void onClick (ClickEvent event) {
                value.update(!value.get());
            }
        };
    }
}
