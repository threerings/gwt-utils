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
     * Creates an info popup with the supplied message. It will automatically dismiss itself after
     * a time proportional to the length of the message has elapsed.
     */
    public InfoPopup (String message)
    {
        this(new Label(message));
        _autoClearTimeout = Math.max(MIN_AUTO_CLEAR_DELAY, message.length() * PER_CHAR_CLEAR_DELAY);
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
        Timer autoClear = new Timer() {
            public void run () {
                hide();
            }
        };
        autoClear.schedule(_autoClearTimeout);
    }

    // we use an int here because that's what Timer wants; whee!
    protected int _autoClearTimeout = DEFAULT_AUTO_CLEAR_DELAY;

    protected static final int MIN_AUTO_CLEAR_DELAY = 3000;
    protected static final int DEFAULT_AUTO_CLEAR_DELAY = 5000;
    protected static final int PER_CHAR_CLEAR_DELAY = 50;

    protected static final int NEAR_GAP = 5;
}
