//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.util.Value;

/**
 * Handles the binding of various widget states to dynamic {@link Value}s.
 */
public class Bindings
{
    /**
     * Binds the enabledness state of the target widget to the supplied boolean value.
     */
    public static void bindEnabled (Value<Boolean> value, final FocusWidget... targets)
    {
        value.addListener(new Value.Listener<Boolean>() {
            public void valueChanged (Boolean enabled) {
                for (FocusWidget target : targets) {
                    target.setEnabled(enabled);
                }
            }
        });
    }

    /**
     * Binds the visible state of the target widget to the supplied boolean value.
     */
    public static void bindVisible (Value<Boolean> value, final Widget... targets)
    {
        value.addListener(new Value.Listener<Boolean>() {
            public void valueChanged (Boolean visible) {
                for (Widget target : targets) {
                    target.setVisible(visible);
                }
            }
        });
    }
}
