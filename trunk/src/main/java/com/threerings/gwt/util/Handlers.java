//
// $Id$
//
// OOO GWT Utils - utilities for creating GWT applications
// Copyright (C) 2009-2010 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/ooo-gwt-utils/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

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
