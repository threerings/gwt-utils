//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.threerings.gwt.util.Console;
import com.google.gwt.user.client.Window;
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
     * Centers the supplied vertically on the supplied trigger widget. The popup will be shown if
     * it is not already.
     */
    public static void centerOn (PopupPanel popup, Widget centerOn)
    {
        centerOn(popup, centerOn.getAbsoluteTop() + centerOn.getOffsetHeight()/2);
    }

    /**
     * Centers the supplied vertically on the supplied trigger widget. The popup will be shown if
     * it is not already.
     */
    public static void centerOn (PopupPanel popup, int ypos)
    {
        popup.setVisible(false);
        popup.show();
        int left = (Window.getClientWidth() - popup.getOffsetWidth()) >> 1;
        int top = ypos - popup.getOffsetHeight()/2;
        // bound the popup into the visible browser area if possible
        if (popup.getOffsetHeight() < Window.getClientHeight()) {
            top = Math.min(Math.max(0, top), Window.getClientHeight() - popup.getOffsetHeight());
        }
        popup.setPopupPosition(left, top);
        popup.setVisible(true);
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

    /**
     * Creates a click handler that hides the specified popup. Useful when creating popups that
     * behave like menus.
     */
    public static ClickHandler createHider (final PopupPanel popup)
    {
        return new ClickHandler() {
            public void onClick (ClickEvent event) {
                popup.hide();
            }
        };
    }

    protected static final int NEAR_GAP = 5;
}
