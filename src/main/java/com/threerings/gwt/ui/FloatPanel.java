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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A panel that lines up it's contents horizontally and wraps them around at the end of lines.
 * Child widgets will be assigned the style attribute "float: left" when added and it will be
 * removed when they are. This is a good div and css using alternative to HorizontalPanel.
 */
public class FloatPanel extends FlowPanel
{
    public FloatPanel (String styleName)
    {
        super();
        if (styleName != null) {
            setStyleName(styleName);
        }

        SimplePanel clear = new SimplePanel();
        clear.setStyleName("fpClear");
        super.add(clear);
    }

    @Override // from FlowPanel
    public void add (Widget widget)
    {
        // insert it at the end, before the clear box
        insert(widget, this.getWidgetCount() - 1);
    }

    @Override // from FlowPanel
    public void insert (Widget widget, int beforeIndex)
    {
        widget.addStyleName("fpFloatLeft");
        super.insert(widget, beforeIndex);
    }

    @Override // from FlowPanel
    public boolean remove (Widget widget)
    {
        if (super.remove(widget)) {
            widget.removeStyleName("fpFloatLeft");
            return true;
        }
        return false;
    }
}
