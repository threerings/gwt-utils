//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;

/**
 * {@link Command} related utility methods.
 */
public class Commands
{
    /**
     * Returns a {@link ClickHandler} that executes the supplied command when triggered.
     */
    public static ClickHandler onClick (final Command onClick)
    {
        return new ClickHandler() {
            public void onClick (ClickEvent event) {
                onClick.execute();
            }
        };
    }
}
