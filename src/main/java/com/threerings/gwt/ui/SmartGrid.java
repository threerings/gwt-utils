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

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

/**
 * Extends {@link Grid} with a number of extremely useful utility methods.
 *
 * See {@link SmartTable}.
 */
public class SmartGrid extends Grid
{
    public SmartGrid (int rows, int columns)
    {
        super(rows, columns);
    }

    public SmartGrid (int rows, int columns, int cellPadding, int cellSpacing)
    {
        super(rows, columns);
        setCellPadding(cellPadding);
        setCellSpacing(cellSpacing);
    }

    public SmartGrid (int rows, int columns, String styleName, int cellPadding, int cellSpacing)
    {
        super(rows, columns);
        setStyleName(styleName);
        setCellPadding(cellPadding);
        setCellSpacing(cellSpacing);
    }

    /**
     * Sets the text in the specified cell, with the specified style and column span.
     */
    public void setText (int row, int column, String text, String style)
    {
        setText(row, column, text);
        if (style != null) {
            getCellFormatter().setStyleName(row, column, style);
        }
    }

    /**
     * Sets the widget in the specified cell, with the specified style and column span.
     */
    public void setWidget (int row, int column, Widget widget, String style)
    {
        setWidget(row, column, widget);
        if (style != null) {
            getCellFormatter().setStyleName(row, column, style);
        }
    }
}
