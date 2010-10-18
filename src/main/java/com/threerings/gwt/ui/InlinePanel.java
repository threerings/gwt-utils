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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A flow panel that applies the "inline" style and a bit of space to every widget added to it.
 * Good for making text bits flow horizontally eg [item name] [by username], but bad for images.
 */
public class InlinePanel extends FlowPanel
{
    public InlinePanel (String styleName)
    {
        super();
        if (styleName != null) {
            setStyleName(styleName);
        }
    }

    @Override public void add (Widget widget)
    {
        widget.addStyleName("inline" + ((getWidgetCount() > 0) ? "L" : ""));
        super.add(widget);
    }

    @Override public void insert (Widget widget, int beforeIndex)
    {
        if (beforeIndex == 0) {
            if (getWidgetCount() > 0) {
                getWidget(0).removeStyleName("inline");
                getWidget(0).addStyleName("inlineL");
            }
            widget.addStyleName("inline");
        } else {
            widget.addStyleName("inlineL");
        }
        super.insert(widget, beforeIndex);
    }

    @Override public boolean remove (Widget widget)
    {
        int oindex = getWidgetIndex(widget);
        if (super.remove(widget)) {
            if (oindex == 0 && getWidgetCount() > 0) {
                getWidget(0).removeStyleName("inlineL");
                getWidget(0).addStyleName("inline");
            }
            return true;
        }
        return false;
    }
}
