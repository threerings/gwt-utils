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

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FileUpload;

/**
 * GWT didn't see fit to expose change events to {@link FileUpload}, so we do that ourselves.
 */
public class SmartFileUpload extends FileUpload
    implements HasValueChangeHandlers<String>
{
    public SmartFileUpload ()
    {
        DOM.sinkEvents(getElement(), Event.ONCHANGE);
    }

    @Override
    public void onBrowserEvent (Event e)
    {
        switch (DOM.eventGetType(e)) {
        case Event.ONCHANGE:
            ValueChangeEvent.fire(this, getFilename());
            break;
        }
    }

    public HandlerRegistration addValueChangeHandler (ValueChangeHandler<String> handler)
    {
        return addHandler(handler, ValueChangeEvent.getType());
    }
}
