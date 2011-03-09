//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasText;

import com.threerings.gwt.util.InputException;

/**
 * Methods for gathering required user input prior to taking an action. A typical method pattern
 * here is to retrieve and validate some widget's value, guaranteeing the return value will be
 * suitably constrained. And if the value is not constrained, inform the user and throw
 * {@link InputException}.
 */
public class InputUtil
{
    /**
     * Returns a text widget's value, making sure it is non-empty.
     * @throws InputException if the widget has no text
     */
    public static <T extends FocusWidget & HasText> String requireStr (T widget, String error)
    {
        String text = widget.getText().trim();
        if (text == null || text.length() == 0) {
            Popups.errorBelow(error, widget);
            widget.setFocus(true);
            throw new InputException();
        }
        return text;
    }
}
