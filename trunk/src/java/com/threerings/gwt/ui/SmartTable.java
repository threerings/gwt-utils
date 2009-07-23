//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

/**
 * Extends {@link FlexTable} with a number of extremely useful utility methods.
 */
public class SmartTable extends FlexTable
{
    public SmartTable ()
    {
    }

    public SmartTable (int cellPadding, int cellSpacing)
    {
        setCellPadding(cellPadding);
        setCellSpacing(cellSpacing);
    }

    public SmartTable (String styleName, int cellPadding, int cellSpacing)
    {
        setStyleName(styleName);
        setCellPadding(cellPadding);
        setCellSpacing(cellSpacing);
    }

    /**
     * Sets the text in the specified cell, with the specified style and column span.
     *
     * @param text an object whose string value will be displayed.
     */
    public void setText (int row, int column, Object text, int colSpan, String... styles)
    {
        setText(row, column, String.valueOf(text));
        if (colSpan > 0) {
            getFlexCellFormatter().setColSpan(row, column, colSpan);
        }
        setStyleNames(row, column, styles);
    }

    /**
     * Sets the HTML in the specified cell, with the specified style and column span.
     */
    public void setHTML (int row, int column, String text, int colSpan, String... styles)
    {
        setHTML(row, column, text);
        if (colSpan > 0) {
            getFlexCellFormatter().setColSpan(row, column, colSpan);
        }
        setStyleNames(row, column, styles);
    }

    /**
     * Sets the widget in the specified cell, with the specified style and column span.
     */
    public void setWidget (int row, int column, Widget widget, int colSpan, String... styles)
    {
        setWidget(row, column, widget);
        if (colSpan > 0) {
            getFlexCellFormatter().setColSpan(row, column, colSpan);
        }
        setStyleNames(row, column, styles);
    }

    /**
     * Adds text to the bottom row of this table in column zero, with the specified column span and
     * style.
     *
     * @param text an object whose string value will be displayed.
     *
     * @return the row to which the text was added.
     */
    public int addText (Object text, int colSpan, String... styles)
    {
        int row = getRowCount();
        setText(row, 0, text, colSpan, styles);
        return row;
    }

    /**
     * Adds a widget to the bottom row of this table in column zero, with the specified column span
     * and style.
     *
     * @return the row to which the widget was added.
     */
    public int addWidget (Widget widget, int colSpan, String... styles)
    {
        int row = getRowCount();
        setWidget(row, 0, widget, colSpan, styles);
        return row;
    }

    /**
     * Configures the specified style names on the specified row and column. The first style is set
     * as the primary style and additional styles are added onto that.
     */
    public void setStyleNames (int row, int column, String... styles)
    {
        int idx = 0;
        for (String style : styles) {
            if (idx++ == 0) {
                getFlexCellFormatter().setStyleName(row, column, style);
            } else {
                getFlexCellFormatter().addStyleName(row, column, style);
            }
        }
    }
}
