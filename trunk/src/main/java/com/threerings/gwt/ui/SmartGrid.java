//
// $Id$

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
