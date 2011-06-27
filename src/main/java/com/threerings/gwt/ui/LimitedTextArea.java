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
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.gwt.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * A text area with a character limit that displays the limit to the user.
 */
public class LimitedTextArea extends VerticalPanel
    implements HasText, HasAllKeyHandlers, HasChangeHandlers
{
    public LimitedTextArea (int maxChars, int width, int height)
    {
        setStyleName("gwt-LimitedTextArea");
        _maxChars = maxChars;

        add(_area = new TextArea());
        _area.addKeyUpHandler(_limiter);
        if (width > 0) {
            _area.setCharacterWidth(width);
        }
        _area.setVisibleLines(height);
        setHorizontalAlignment(ALIGN_RIGHT);
        add(_remaining = new Label());
        _remaining.setStyleName("Remaining");
        updateRemaining();
    }

    public void setText (String text)
    {
        // we'd use a change listener here but browsers conveniently don't report a change event
        // when a text area's text changes, awesome!
        _area.setText(text);
        updateRemaining();
    }

    public String getText ()
    {
        return _area.getText();
    }

    /**
     * Returns the text area being limited. Don't call {@link TextArea#setText} directly on this
     * area. Use {@link #setText}.
     */
    public TextArea getTextArea ()
    {
        return _area;
    }

    public HandlerRegistration addKeyDownHandler (KeyDownHandler handler)
    {
        return _area.addKeyDownHandler(handler);
    }

    public HandlerRegistration addKeyUpHandler (KeyUpHandler handler)
    {
        return _area.addKeyUpHandler(handler);
    }

    public HandlerRegistration addKeyPressHandler (KeyPressHandler handler)
    {
        return _area.addKeyPressHandler(handler);
    }

    public HandlerRegistration addChangeHandler (ChangeHandler handler)
    {
        return _area.addChangeHandler(handler);
    }

    protected void updateRemaining ()
    {
        String text = _area.getText();
        if (text.length() > _maxChars) {
            _area.setText(text = text.substring(0, _maxChars));
        }
        _remaining.setText(_msgs.charRemaining(String.valueOf(_maxChars - text.length())));
    }

    protected KeyUpHandler _limiter = new KeyUpHandler() {
        public void onKeyUp (KeyUpEvent event) {
            updateRemaining();
        }
    };

    protected int _maxChars;
    protected TextArea _area;
    protected Label _remaining;

    protected static final UIMessages _msgs = GWT.create(UIMessages.class);
}
