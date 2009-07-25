//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.PopupPanel;
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
        new InfoPopup(message).toError().showCentered();
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
        new InfoPopup(message).toError().showNear(source);
    }

    /**
     * Shows the supplied popup panel near the specified target.
     */
    public static void showNear (PopupPanel popup, Widget target)
    {
        popup.setPopupPosition(target.getAbsoluteLeft(),
                               target.getAbsoluteTop() + target.getOffsetHeight() + NEAR_GAP);
        popup.show();
    }

    /**
     * Shows the supplied popup panel over the specified target.
     */
    public static void showOver (PopupPanel popup, Widget target)
    {
        popup.setPopupPosition(target.getAbsoluteLeft(), target.getAbsoluteTop());
        popup.show();
    }

    /**
     * Creates and returns a new popup with the specified style name and contents.
     */
    public static PopupPanel newPopup (String styleName, Widget contents)
    {
        PopupPanel panel = new PopupPanel();
        panel.setStyleName(styleName);
        panel.setWidget(contents);
        return panel;
    }

    /**
     * Creates a new popup with the specified style name and contents and shows it near the
     * specified target widget. Returns the newly created popup.
     */
    public static PopupPanel newPopupNear (String styleName, Widget contents, Widget target)
    {
        PopupPanel panel = newPopup(styleName, contents);
        showNear(panel, target);
        return panel;
    }

    protected static final int NEAR_GAP = 5;
}
