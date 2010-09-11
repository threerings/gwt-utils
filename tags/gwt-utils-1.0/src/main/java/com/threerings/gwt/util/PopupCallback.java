//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.ui.Popups;

/**
 * A base class for callbacks that automatically report errors via {@link Popups#error} or {@link
 * Popups#errorNear} as well as logs the raw error to the {@link Console}.
 */
public abstract class PopupCallback<T> implements AsyncCallback<T>
{
    // from AsyncCallback<T>
    public void onFailure (Throwable cause)
    {
        if (_errorNear == null) {
            Popups.error(formatError(cause));
        } else {
            Popups.errorNear(formatError(cause), _errorNear);
        }
        Console.log("Service request failed", cause);
    }

    /**
     * Creates a callback that will display its error in the middle of the page.
     */
    protected PopupCallback ()
    {
    }

    /**
     * Creates a callback that will display its error near the supplied widget.
     */
    protected PopupCallback (Widget errorNear)
    {
        _errorNear = errorNear;
    }

    /**
     * Formats the error indicated by the supplied throwable. The default implementation simply
     * returns {@link Throwable#getMessage}.
     */
    protected String formatError (Throwable cause)
    {
        return cause.getMessage();
    }

    protected Widget _errorNear;
}
