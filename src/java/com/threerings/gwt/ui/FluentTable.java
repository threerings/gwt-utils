//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Widget;

/**
 * Extends {@link FlexTable} and provides a fluent interface for adjusting the styles of cells.
 */
public class FluentTable extends FlexTable
{
    /** Used to format cells. Returned by all methods that configure cells. */
    public static class Formatter
    {
        /** Sets the rowspan of the cell we're formatting. */
        public Formatter setRowSpan (int rowSpan) {
            _formatter.setRowSpan(_row, _col, rowSpan);
            return this;
        }

        /** Sets the colspan of the cell we're formatting. */
        public Formatter setColSpan (int colSpan) {
            _formatter.setColSpan(_row, _col, colSpan);
            return this;
        }

        /** Makes the cell we're formatting align top. */
        public Formatter alignTop () {
            _formatter.setVerticalAlignment(_row, _col, HasAlignment.ALIGN_TOP);
            return this;
        }

        /** Makes the cell we're formatting align bottom. */
        public Formatter alignBottom () {
            _formatter.setVerticalAlignment(_row, _col, HasAlignment.ALIGN_BOTTOM);
            return this;
        }

        /** Makes the cell we're formatting align middle. */
        public Formatter alignMiddle () {
            _formatter.setVerticalAlignment(_row, _col, HasAlignment.ALIGN_MIDDLE);
            return this;
        }

        /** Makes the cell we're formatting align left. */
        public Formatter alignLeft () {
            _formatter.setHorizontalAlignment(_row, _col, HasAlignment.ALIGN_LEFT);
            return this;
        }

        /** Makes the cell we're formatting align right. */
        public Formatter alignRight () {
            _formatter.setHorizontalAlignment(_row, _col, HasAlignment.ALIGN_RIGHT);
            return this;
        }

        /** Makes the cell we're formatting align center. */
        public Formatter alignCenter () {
            _formatter.setHorizontalAlignment(_row, _col, HasAlignment.ALIGN_CENTER);
            return this;
        }

        /**
         * Configures the specified style names on our cell. The first style is set as the primary
         * style and additional styles are added onto that.
         */
        public Formatter setStyleNames (String... styles)
        {
            int idx = 0;
            for (String style : styles) {
                if (idx++ == 0) {
                    _formatter.setStyleName(_row, _col, style);
                } else {
                    _formatter.addStyleName(_row, _col, style);
                }
            }
            return this;
        }

        protected Formatter (FlexCellFormatter formatter, int row, int col)
        {
            _formatter = formatter;
            _row = row;
            _col = col;
        }

        protected FlexCellFormatter _formatter;
        protected int _row, _col;
    }

    public FluentTable ()
    {
    }

    public FluentTable (int cellPadding, int cellSpacing)
    {
        setCellPadding(cellPadding);
        setCellSpacing(cellSpacing);
    }

    public FluentTable (String styleName, int cellPadding, int cellSpacing)
    {
        setStyleName(styleName);
        setCellPadding(cellPadding);
        setCellSpacing(cellSpacing);
    }

    /**
     * Sets the text in the specified cell, with the specified optional styles.
     *
     * @param text an object whose string value will be displayed.
     */
    public Formatter setText (int row, int column, Object text, String... styles)
    {
        setText(row, column, String.valueOf(text));
        return new Formatter(getFlexCellFormatter(), row, column).setStyleNames(styles);
    }

    /**
     * Sets the HTML in the specified cell, with the specified optional styles.
     */
    public Formatter setHTML (int row, int column, String text, String... styles)
    {
        setHTML(row, column, text);
        return new Formatter(getFlexCellFormatter(), row, column).setStyleNames(styles);
    }

    /**
     * Sets the widget in the specified cell, with the specified optional styles.
     */
    public Formatter setWidget (int row, int column, Widget widget, String... styles)
    {
        setWidget(row, column, widget);
        return new Formatter(getFlexCellFormatter(), row, column).setStyleNames(styles);
    }

    /**
     * Adds text to the bottom row of this table in column zero, with the specified optional styles.
     *
     * @param text an object whose string value will be displayed.
     */
    public Formatter addText (Object text, String... styles)
    {
        return setText(getRowCount(), 0, text, styles);
    }

    /**
     * Adds a widget to the bottom row of this table in column zero, with the specified optional
     * styles and style.
     */
    public Formatter addWidget (Widget widget, String... styles)
    {
        return setWidget(getRowCount(), 0, widget, styles);
    }
}
