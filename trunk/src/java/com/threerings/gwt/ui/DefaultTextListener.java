//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * Displays default text in a text box or area and clears that text out when the box is focused.
 */
public class DefaultTextListener
    implements FocusHandler, BlurHandler
{
    /**
     * Configures the target text box to display the supplied default text when it does not have
     * focus and to clear it out when the user selects it to enter text.
     */
    public static void configure (TextBoxBase target, String defaultText)
    {
        DefaultTextListener listener = new DefaultTextListener(target, defaultText);
        target.addFocusHandler(listener);
        target.addBlurHandler(listener);
    }

    /**
     * Returns the contents of the supplied text box, accounting for the supplied default text.
     */
    public static String getText (TextBoxBase target, String defaultText)
    {
        String text = target.getText().trim();
        return text.equals(defaultText.trim()) ? "" : text;
    }

    // from interface FocusHandler
    public void onFocus (FocusEvent event)
    {
        if (_target.getText().equals(_defaultText)) {
            _target.setText("");
        }
    }

    // from interface BlurHandler
    public void onBlur (BlurEvent event)
    {
        if (_target.getText().trim().equals("")) {
            _target.setText(_defaultText);
        }
    }

    protected DefaultTextListener (TextBoxBase target, String defaultText)
    {
        _target = target;
        _defaultText = defaultText;
        if (_target.getText().trim().equals("")) {
            _target.setText(defaultText);
        }
    }

    protected TextBoxBase _target;
    protected String _defaultText;
}
