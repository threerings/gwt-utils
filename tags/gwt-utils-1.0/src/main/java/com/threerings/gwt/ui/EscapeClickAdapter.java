//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

/**
 * Converts an escape keypress in a text field to a call to a click handler. NOTE: the handler will
 * be passed a null event.
 */
public class EscapeClickAdapter implements KeyPressHandler
{
    public EscapeClickAdapter (ClickHandler onEscape)
    {
        _onEscape = onEscape;
    }

    // from interface KeyPressHandler
    public void onKeyPress (KeyPressEvent event)
    {
        if (event.getCharCode() == KeyCodes.KEY_ESCAPE) {
            _onEscape.onClick(null);
        }
    }

    protected ClickHandler _onEscape;
}
