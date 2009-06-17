//
// $Id$

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
