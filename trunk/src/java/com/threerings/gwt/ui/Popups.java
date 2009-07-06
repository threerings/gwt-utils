//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Popup-related utility methods.
 */
public class Popups
{
    /**
     * Displays an info message.
     */
    public static void info (String message)
    {
        new InfoPopup(message).showCentered();
    }

    /**
     * Displays an info message near the specified widget.
     */
    public static void infoNear (String message, Widget target)
    {
        new InfoPopup(message).showNear(target);
    }

    /**
     * Displays error feedback to the user in a non-offensive way.
     */
    public static void error (String message)
    {
        // TODO: style this differently than info feedback
        new InfoPopup(message).showCentered();
    }

    /**
     * Displays error feedback to the user in a non-offensive way. The error feedback is displayed
     * near the supplied component and if the component supports focus, it is focused.
     */
    public static void errorNear (String message, Widget source)
    {
        if (source instanceof FocusWidget) {
            ((FocusWidget)source).setFocus(true);
        }
        // TODO: style this differently than info feedback
        new InfoPopup(message).showNear(source);
    }
}
