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

package com.threerings.gwt.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays a confirmation popup.
 */
public class ConfirmPopup extends PopupPanel
{
    /**
     * Creates an info popup with the supplied message.
     */
    public ConfirmPopup (String message, String ok, String cancel, ClickHandler handler)
    {
        this(new Label(message), ok, cancel, handler);
    }

    /**
     * Creates a a confirm popup with all the fields customized.
     */
    public ConfirmPopup (Widget message, String ok, String cancel, final ClickHandler handler)
    {
        super(false, true);
        setStyleName("confirmPopup");

        final Button okBtn = new Button(ok, new ClickHandler() {
                public void onClick (ClickEvent event) {
                    hide();
                    handler.onClick(event);
                }
            });
        final Button cancelBtn = new Button(cancel, new ClickHandler() {
                public void onClick (ClickEvent event) {
                    hide();
                }
            });

        FluentTable table = new FluentTable();
        table.add().setColSpan(2).setWidget(message)
                .add().setWidget(cancelBtn)
                .right().setWidget(okBtn);
        setWidget(table);
    }
}
