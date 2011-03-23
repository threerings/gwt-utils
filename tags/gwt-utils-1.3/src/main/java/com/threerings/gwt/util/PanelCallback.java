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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

import com.threerings.gwt.ui.Widgets;

/**
 * A base class for callbacks that automatically report errors by clearing a target {@link Panel}
 * and adding a {@link Label} to it. The label will be styled as <code>errorLabel</code>, a basic
 * style for which is defined in the CSS file accompanying this library.
 */
public abstract class PanelCallback<T> implements AsyncCallback<T>
{
    // from AsyncCallback<T>
    public void onFailure (Throwable cause)
    {
        _panel.clear();
        _panel.add(Widgets.newLabel(formatError(cause), "errorLabel"));
        Console.log("Service request failed", cause);
    }

    protected PanelCallback (Panel panel)
    {
        _panel = panel;
    }

    /**
     * Formats the error indicated by the supplied throwable. The default implementation simply
     * returns {@link Throwable#getMessage}.
     */
    protected String formatError (Throwable cause)
    {
        return cause.getMessage();
    }

    protected Panel _panel;
}
