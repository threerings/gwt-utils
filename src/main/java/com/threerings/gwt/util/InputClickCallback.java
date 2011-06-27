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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.ui.EnterClickAdapter;
import com.threerings.gwt.ui.SmartTable;

/**
 * Provides a click handler that will first request an input string from the user using a popup
 * and then perform a service call with the input string. The input widget is provided on
 * construction and the normally anonymous subclass supplies the service handling.
 *
 * <p>NOTE: the input popup replaces the confirmation popup.</p>
 */
public abstract class InputClickCallback<
        T, W extends Widget & HasText & HasKeyDownHandlers & HasKeyUpHandlers>
    extends ClickCallback<T>
{
    /**
     * Creates a new click handler for the given trigger. Upon clicking, text input will be
     * requested from the user by popping up a confirmation with the given widget.
     */
    public InputClickCallback (HasClickHandlers trigger, W textInputWidget)
    {
        super(trigger);
        setConfirmText("");
        _widget = textInputWidget;
    }

    /**
     * Sets the text to be shown above the input explaining what the user should do. Note that this
     * just calls {@link #setConfirmText(String)}, but is included here for clarity.
     */
    public InputClickCallback<T, W> setPromptText(String prompt)
    {
        setConfirmText(prompt);
        return this;
    }

    /**
     * Sets the html to be shown above the input explaining what the user should do. Note that this
     * just calls {@link #setConfirmHTML(String)}, but is included here for clarity.
     */
    public InputClickCallback<T, W> setPromptHTML(String promptHTML)
    {
        setConfirmHTML(promptHTML);
        return this;
    }

    /**
     * Sets whether or not pressing the enter key while typing will cause the confirmation to
     * occur. By default, the enter key is bound.
     */
    public InputClickCallback<T, W> setBindEnterKey(boolean value)
    {
        _bindEnterKey = value;
        return this;
    }

    @Override
    protected boolean callService ()
    {
        return callService(_widget.getText());
    }

    /**
     * Makes the asynchronous service call, returning true only if the call was actually made.
     * @see #callService()
     */
    abstract protected boolean callService (String input);

    @Override
    protected void onAborted ()
    {
        super.onAborted();
        removeHandlerReg();
    }

    @Override
    protected void onConfirmed ()
    {
        super.onConfirmed();
        removeHandlerReg();
    }

    /**
     * Removes the change handler registration that was added for the confirmation popup.
     */
    protected void removeHandlerReg ()
    {
        _handlerReg.removeHandler();
        _handlerReg = null;
    }

    @Override
    protected int addConfirmPopupMessage (SmartTable contents, int row)
    {
        row = super.addConfirmPopupMessage(contents, row);
        contents.setWidget(row, 0, _widget);
        contents.getFlexCellFormatter().setColSpan(row, 0, 2);
        row++;

        contents.setWidget(row, 0, _widget);
        contents.getFlexCellFormatter().setColSpan(row, 0, 2);
        contents.getFlexCellFormatter().setWidth(row, 0, "100%");
        row++;

        return row;
    }

    @Override
    protected ButtonBase createConfirmButton (String text, ClickHandler onClick)
    {
        final ButtonBase button = super.createConfirmButton(text, onClick);
        if (_bindEnterKey) {
            EnterClickAdapter.bind(_widget, onClick);
        }
        KeyUpHandler handler = new KeyUpHandler() {
            @Override public void onKeyUp (KeyUpEvent event) {
                String text = _widget.getText();
                button.setEnabled(text != null && text.length() > 0);
            }
        };
        _handlerReg = _widget.addKeyUpHandler(handler);
        handler.onKeyUp(null);
        return button;
    }

    protected W _widget;
    protected boolean _bindEnterKey;
    protected HandlerRegistration _handlerReg;
}
