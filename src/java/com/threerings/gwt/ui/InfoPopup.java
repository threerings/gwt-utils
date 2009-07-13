//
// $Id$

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

    /**
     * Displays this info popup directly below the specified widget.
     */
    public void showNear (Widget parent)
    {
        setPopupPosition(parent.getAbsoluteLeft(),
                         parent.getAbsoluteTop() + parent.getOffsetHeight() + NEAR_GAP);
        show();
    }

    /**
     * Displays this info popup in the center of the page.
     */
    public void showCentered ()
    {
        center(); // this will show us
    }

    protected void onAttach ()
    {
        super.onAttach();
        new Timer() {
            public void run () {
                hide();
            }
        }.schedule(_autoClearTimeout);
    }

    // we use an int here because that's what Timer wants; whee!
    protected int _autoClearTimeout = DEFAULT_AUTO_CLEAR_DELAY;

    protected static final int MIN_AUTO_CLEAR_DELAY = 3000;
    protected static final int DEFAULT_AUTO_CLEAR_DELAY = 5000;
    protected static final int PER_CHAR_CLEAR_DELAY = 50;

    protected static final int NEAR_GAP = 5;
}
