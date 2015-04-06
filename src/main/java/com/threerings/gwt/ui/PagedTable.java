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

import com.google.gwt.user.client.ui.Widget;

/**
 * Displays a table of UI elements in pages.
 */
public abstract class PagedTable<T> extends PagedWidget<T>
{
    public PagedTable (int rows)
    {
        this(rows, NAV_ON_TOP);
    }

    public PagedTable (int rows, int navLoc)
    {
        super(rows, navLoc);

        _rows = rows;

        setStyleName("pagedGrid"); // Yoink
    }

    @Override
    protected SmartTable createContents (int start, int count, List<T> list)
    {
        SmartTable table = new SmartTable();
        table.setWidth("100%");

        if (addRow(table, createHeader())) {
            table.getRowFormatter().setStyleName(0, "header");
        }

        for (int row=0; row<list.size() && row<count; ++row) {
            if (addRow(table, createRow(list.get(row)))) {
                table.getRowFormatter().setStyleName(table.getRowCount()-1, "row" + (row%2));
                didAddRow(table, table.getRowCount()-1, list.get(row));
            }
        }

        return table;
    }

    /** Convenience function to append a list of widgets to a table as a new row. */
    protected static boolean addRow (SmartTable table, List<Widget> widgets)
    {
        if (widgets == null) {
            return false;
        }
        int row = table.getRowCount();
        for (int col=0; col<widgets.size(); ++col) {
            table.setWidget(row, col, widgets.get(col));
            table.getFlexCellFormatter().setStyleName(row, col, "col"+col);
        }
        return true;
    }

    protected List<Widget> createHeader ()
    {
        // By default, don't show a header
        return null;
    }

    /**
     * Lets subclasses know that a row was just added, for example to customize row styles.
     */
    protected void didAddRow (SmartTable table, int row, T item)
    {
    }

    /** From a data item, create a table row in the form of a list of widgets. */
    protected abstract List<Widget> createRow (T item);

    protected int _rows;
}
