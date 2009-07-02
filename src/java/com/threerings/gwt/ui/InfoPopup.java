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
    public InfoPopup (String message)
    {
        this(new Label(message));
        _autoClearTimeout = Math.max(MIN_AUTO_CLEAR_DELAY, message.length() * PER_CHAR_CLEAR_DELAY);
    }

    public InfoPopup (Widget contents)
    {
        super(true);
        setStyleName("infoPopup");
        setWidget(contents);
    }

    public void showNear (Widget parent)
    {
        setPopupPosition(parent.getAbsoluteLeft(),
                         parent.getAbsoluteTop() + parent.getOffsetHeight() + NEAR_GAP);
        show();
    }

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
