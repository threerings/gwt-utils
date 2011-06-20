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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Converts an enter keydown in a text field to a call to a click handler. NOTE: the handler will
 * be passed a null event.
 */
public class EnterClickAdapter implements KeyDownHandler
{
    /**
     * Binds a listener to the supplied target text box that triggers the supplied click handler
     * when enter is pressed on the text box.
     */
    public static HandlerRegistration bind (HasKeyDownHandlers target, ClickHandler onEnter)
    {
        return target.addKeyDownHandler(new EnterClickAdapter(onEnter));
    }

    public EnterClickAdapter (ClickHandler onEnter)
    {
        _onEnter = onEnter;
    }

    // from interface KeyDownHandler
    public void onKeyDown (KeyDownEvent event)
    {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
            _onEnter.onClick(null);
        }
    }

    protected ClickHandler _onEnter;
}
