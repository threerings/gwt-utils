//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.ui.Popups;

/**
 * A base class for callbacks that automatically report errors via {@link Popups#error} or {@link
 * Popups#errorNear} as well as logs the raw error to the {@link Console}. A project should extend
 * this and implement {@link #formatError} to create a PopupCallback class that it can use in place
 * of {@link AsyncCallback}.
 */
public abstract class AbstractPopupCallback<T> implements AsyncCallback<T>
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
    protected AbstractPopupCallback ()
    {
    }

    /**
     * Creates a callback that will display its error near the supplied widget.
     */
    protected AbstractPopupCallback (Widget errorNear)
    {
        _errorNear = errorNear;
    }

    protected abstract String formatError (Throwable cause);

    protected Widget _errorNear;
}
