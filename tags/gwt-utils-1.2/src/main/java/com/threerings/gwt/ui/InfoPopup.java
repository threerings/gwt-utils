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

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays a popup informational message.
 */
public class InfoPopup extends PopupPanel
{
    /**
     * Computes a reasonable delay after which it should be safe to automatically clear a transient
     * informational message.
     */
    public static int computeAutoClearDelay (String message)
    {
        return Math.max(MIN_AUTO_CLEAR_DELAY, message.length() * PER_CHAR_CLEAR_DELAY);
    }

    /**
     * Creates an info popup with the supplied message. It will automatically dismiss itself after
     * a time proportional to the length of the message has elapsed.
     */
    public InfoPopup (String message)
    {
        this(new Label(message));
        _autoClearTimeout = computeAutoClearDelay(message);
    }

    /**
     * Creates an info popup with the supplied contents.
     */
    public InfoPopup (Widget contents)
    {
        super(true);
        setStyleName("infoPopup");
        setWidget(contents);
    }

    /**
     * Converts this info popup into an error popup (via a CSS style change) and returns self.
     */
    public InfoPopup toError ()
    {
        setStyleName("errorPopup");
        return this;
    }

    /** @deprecated Use {@link #show(Popups.Position,Widget)} */ @Deprecated
    public void showNear (Widget target)
    {
        show(Popups.Position.BELOW, target);
    }

    /**
     * Displays this info popup directly below the specified widget.
     */
    public void show (Popups.Position pos, Widget target)
    {
        Popups.show(this, pos, target);
    }

    /**
     * Displays this info popup in the center of the page.
     */
    public void showCentered ()
    {
        center(); // this will show us
    }

    @Override
    protected void onAttach ()
    {
        super.onAttach();
        new Timer() {
            @Override public void run () {
                hide();
            }
        }.schedule(_autoClearTimeout);
    }

    // we use an int here because that's what Timer wants; whee!
    protected int _autoClearTimeout = DEFAULT_AUTO_CLEAR_DELAY;

    protected static final int MIN_AUTO_CLEAR_DELAY = 3000;
    protected static final int DEFAULT_AUTO_CLEAR_DELAY = 5000;
    protected static final int PER_CHAR_CLEAR_DELAY = 50;
}
