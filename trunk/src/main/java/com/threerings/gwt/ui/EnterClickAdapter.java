//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

/**
 * Converts an enter keypress in a text field to a call to a click handler. NOTE: the handler will
 * be passed a null event.
 */
public class EnterClickAdapter implements KeyPressHandler
{
    public EnterClickAdapter (ClickHandler onEnter)
    {
        _onEnter = onEnter;
    }

    // from interface KeyPressHandler
    public void onKeyPress (KeyPressEvent event)
    {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
            _onEnter.onClick(null);
        }
    }

    protected ClickHandler _onEnter;
}
