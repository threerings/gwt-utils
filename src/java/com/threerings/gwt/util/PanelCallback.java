//
// $Id$

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
