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
 * style for which is defined in the CSS file accompanying this library. A project should extend
 * this and implement {@link #formatError} to create a PanelCallback class that it can use in place
 * of {@link AsyncCallback}.
 */
public abstract class AbstractPanelCallback<T> implements AsyncCallback<T>
{
    // from AsyncCallback<T>
    public void onFailure (Throwable cause)
    {
        _panel.clear();
        _panel.add(Widgets.newLabel(formatError(cause), "errorLabel"));
        Console.log("Service request failed", cause);
    }

    protected AbstractPanelCallback (Panel panel)
    {
        _panel = panel;
    }

    protected abstract String formatError (Throwable cause);

    protected Panel _panel;
}
