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

import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Helper class that centers a single widget horizontally and vertically. Using a TABLE!
 */
public class CenteredBox extends HorizontalPanel
{
    public CenteredBox (Widget widget, String styleName)
    {
        super();
        if (styleName != null) {
            setStyleName(styleName);
        }
        setVerticalAlignment(HasAlignment.ALIGN_MIDDLE);
        setHorizontalAlignment(HasAlignment.ALIGN_CENTER);
        if (widget != null) {
            add(widget);
        }
    }

    public CenteredBox (Widget widget, String styleName, int width, int height)
    {
        this(widget, styleName);
        if (width > 0) {
            setWidth(width + "px");
        }
        if (height > 0) {
            setHeight(height + "px");
        }
    }
}
