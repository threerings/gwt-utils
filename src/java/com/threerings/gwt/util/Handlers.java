//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Utility methods for handlers (click handlers, etc.).
 */
public class Handlers
{
    /**
     * Creates a click handler that dispatches a single click event to a series of handlers in
     * sequence. If any handler fails, the error will be caught, logged and the other handlers will
     * still have a chance to process the event.
     */
    public static ClickHandler chain (final ClickHandler... handlers)
    {
        return new ClickHandler() {
            public void onClick (ClickEvent event) {
                for (ClickHandler handler : handlers) {
                    try {
                        handler.onClick(event);
                    } catch (Exception e) {
                        Console.log("Chained click handler failed", "handler", handler, e);
                    }
                }
            }
        };
    }
}
