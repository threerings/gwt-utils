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
        /** The row we're formatting. */
        public final int row;

        /** The column we're formatting. */
        public final int column;

        /** Makes the cell we're formatting align top. */
        public Formatter alignTop () {
            _formatter.setVerticalAlignment(row, column, HasAlignment.ALIGN_TOP);
            return this;
        }

        /** Makes the cell we're formatting align bottom. */
        public Formatter alignBottom () {
            _formatter.setVerticalAlignment(row, column, HasAlignment.ALIGN_BOTTOM);
            return this;
        }

        /** Makes the cell we're formatting align middle. */
        public Formatter alignMiddle () {
            _formatter.setVerticalAlignment(row, column, HasAlignment.ALIGN_MIDDLE);
            return this;
        }

        /** Makes the cell we're formatting align left. */
        public Formatter alignLeft () {
            _formatter.setHorizontalAlignment(row, column, HasAlignment.ALIGN_LEFT);
            return this;
        }

        /** Makes the cell we're formatting align right. */
        public Formatter alignRight () {
            _formatter.setHorizontalAlignment(row, column, HasAlignment.ALIGN_RIGHT);
            return this;
        }

        /** Makes the cell we're formatting align center. */
        public Formatter alignCenter () {
            _formatter.setHorizontalAlignment(row, column, HasAlignment.ALIGN_CENTER);
            return this;
        }

        /** Sets the rowspan of the cell we're formatting. */
        public Formatter setRowSpan (int rowSpan) {
            _formatter.setRowSpan(row, column, rowSpan);
            return this;
        }

        /** Sets the colspan of the cell we're formatting. */
        public Formatter setColSpan (int colSpan) {
            _formatter.setColSpan(row, column, colSpan);
            return this;
        }

        /** Configures the specified style names on our cell. The first style is set as the primary
         * style and additional styles are added onto that. */
        public Formatter setStyles (String... styles)
        {
            int idx = 0;
            for (String style : styles) {
                if (idx++ == 0) {
                    _formatter.setStyleName(row, column, style);
                } else {
                    _formatter.addStyleName(row, column, style);
                }
            }
            return this;
        }

        protected Formatter (FlexCellFormatter formatter, int row, int column)
        {
            _formatter = formatter;
            this.row = row;
            this.column = column;
        }

        protected FlexCellFormatter _formatter;
    }

    public FluentTable ()
    {
    }

    public FluentTable (String... styles)
    {
        Widgets.setStyleNames(this, styles);
    }

    public FluentTable (int cellPadding, int cellSpacing)
    {
        setCellPadding(cellPadding);
        setCellSpacing(cellSpacing);
    }

    public FluentTable (int cellPadding, int cellSpacing, String... styles)
    {
        this(styles);
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
        return new Formatter(getFlexCellFormatter(), row, column).setStyles(styles);
    }

    /**
     * Sets the HTML in the specified cell, with the specified optional styles.
     */
    public Formatter setHTML (int row, int column, String text, String... styles)
    {
        setHTML(row, column, text);
        return new Formatter(getFlexCellFormatter(), row, column).setStyles(styles);
    }

    /**
     * Sets the widget in the specified cell, with the specified optional styles.
     */
    public Formatter setWidget (int row, int column, Widget widget, String... styles)
    {
        setWidget(row, column, widget);
        return new Formatter(getFlexCellFormatter(), row, column).setStyles(styles);
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

    /**
     * Wraps the supplied widgets into a FlowPanel and sticks them into the specified cell.
     */
    public Formatter setWidgets (int row, int column, Widget... widgets)
    {
        setWidget(row, column, Widgets.newFlowPanel(widgets));
        return new Formatter(getFlexCellFormatter(), row, column);
    }
}
