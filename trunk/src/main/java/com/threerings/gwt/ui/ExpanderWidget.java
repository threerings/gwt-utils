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

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.util.ExpanderResult;

/**
 * A "click to see more" type of control that displays more elements on a click. This is an
 * alternative to PagedWidget if you don't want most of its features.
 */
public abstract class ExpanderWidget<T> extends FlowPanel
{
    public ExpanderWidget (String expandText)
    {
        this(expandText, true);
    }

    /**
     * @param appendMode True to append pages to the end of the list,
     *     false to prepend to the beginning.
     */
    public ExpanderWidget (String expandText, boolean appendMode)
    {
        _expandLabel = new Label(expandText);
        _expandLabel.addClickHandler(new ClickHandler() {
            public void onClick (ClickEvent event) {
                expand();
            }
        });
        add(_expandLabel);

        // Elements go above the expand button in append mode
        _appendMode = appendMode;
        if (_appendMode) {
            insert(_content, 0);
        } else {
            add(_content);
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
        if (_loading) {
            return;
        }
        _loading = true;

        fetchElements(new AsyncCallback<ExpanderResult<T>>() {
            public void onSuccess (ExpanderResult<T> result) {
                _loading = false;
                _expandLabel.setVisible(result.hasMore);

                addElements(result.page);
            }
            public void onFailure (Throwable error) {
                _loading = false;
                handleError(error);
            }
        });
    }

    public void addElements (List<T> elements)
    {
        addElements(elements, _appendMode);
    }

    public void addElements (List<T> elements, boolean append)
    {
        FlowPanel page = new FlowPanel();
        int ii = 0;
        for (T element : elements) {
            Widget w = createElement(element);
            _elements.put(element, w);

            if (append) {
                _content.add(w);
            } else {
                _content.insert(w, ii++);
            }
        }
    }

    public void removeElement (T element)
    {
        Widget w = _elements.remove(element);
        if (w != null) {
            w.removeFromParent();
        }
    }

    protected Label _expandLabel;
    protected FlowPanel _content = new FlowPanel();
    protected boolean _appendMode;
    protected boolean _loading;

    protected Map<T, Widget> _elements = Maps.newHashMap();
}
