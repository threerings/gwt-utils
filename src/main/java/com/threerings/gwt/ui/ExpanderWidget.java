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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.util.ExpanderResult;

/**
 * A "click to see more" type of control that displays more elements on a click. This is an
 * alternative to PagedWidget if you want most of its features.
 */
public abstract class ExpanderWidget<T> extends FlowPanel
{
    public ExpanderWidget (FocusWidget expandButton)
    {
        _expandButton = expandButton;
        _expandButton.addClickHandler(new ClickHandler() {
            public void onClick (ClickEvent event) {
                expand();
            }
        });
        add(_expandButton);
    }

    protected void displayElements (List<Widget> widgets)
    {
        for (Widget widget : widgets) {
            add(widget);
        }
    }

    protected abstract Widget createElement (T element);

    protected abstract void fetchElements (AsyncCallback<ExpanderResult<T>> callback);

    protected void handleError (Throwable error)
    {
        // Optionally implemented in subclasses
    }

    public void expand ()
    {
        _expandButton.setEnabled(false);

        fetchElements(new AsyncCallback<ExpanderResult<T>>() {
            public void onSuccess (ExpanderResult<T> result) {

                _expandButton.setEnabled(true);
                _expandButton.setVisible(result.hasMore);

                List<Widget> widgets = new ArrayList<Widget>();
                for (T element : result.page) {
                    widgets.add(createElement(element));
                }
                displayElements(widgets);
            }
            public void onFailure (Throwable error) {
                handleError(error);
            }
        });
    }

    protected FocusWidget _expandButton;
}
