//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

/**
 * Extends {@link FlexTable} with a number of extremely useful utility methods.
 */
public class SmartTable extends FlexTable
{
    /**
     * Performs a number of convenient modifications on a cell, each returning the mutator instance
     * for very legible chaining of modifications.
     */
    public class CellMutator
    {
        /**
         * Sets the cell content to the given widget.
         */
        public CellMutator widget (Widget widget)
        {
            setWidget(_row, _col, widget);
            return this;
        }

        /**
         * Sets the cell content to the given text.
         */
        public CellMutator text (String text)
        {
            setText(_row, _col, text);
            return this;
        }

        /**
         * Sets the cell content to the given html.
         */
        public CellMutator html (String html)
        {
            setHTML(_row, _col, html);
            return this;
        }

        /**
         * Sets the colspan attribute of the cell to the given span.
         */
        public CellMutator colSpan (int span)
        {
            getFlexCellFormatter().setColSpan(_row, _col, span);
            return this;
        }

        /**
         * Adds the given styles to the cell.
         */
        public CellMutator styles (String... names)
        {
            setStyleNames(_row, _col, names);
            return this;
        }

        /**
         * Sets the vertical alignment for the cell to "top".
         */
        public CellMutator alignTop ()
        {
            valign(_row, _col, HasVerticalAlignment.ALIGN_TOP);
            return this;
        }

        /**
         * Sets the vertical alignment for the cell to "middle".
         */
        public CellMutator alignMiddle ()
        {
            valign(_row, _col, HasVerticalAlignment.ALIGN_MIDDLE);
            return this;
        }

        /**
         * Sets the vertical alignment for the cell to "bottom".
         */
        public CellMutator alignBottom ()
        {
            valign(_row, _col, HasVerticalAlignment.ALIGN_BOTTOM);
            return this;
        }

        /**
         * Sets the horizontal alignment for the cell to "left".
         */
        public CellMutator alignLeft ()
        {
            halign(_row, _col, HasHorizontalAlignment.ALIGN_LEFT);
            return this;
        }

        /**
         * Sets the horizontal alignment for the cell to "center".
         */
        public CellMutator alignCenter ()
        {
            halign(_row, _col, HasHorizontalAlignment.ALIGN_CENTER);
            return this;
        }

        /**
         * Sets the horizontal alignment for the cell to "right".
         */
        public CellMutator alignRight ()
        {
            halign(_row, _col, HasHorizontalAlignment.ALIGN_RIGHT);
            return this;
        }

        /**
         * Sets the horizontal alignment for the cell to "default".
         */
        public CellMutator alignDefault ()
        {
            halign(_row, _col, HasHorizontalAlignment.ALIGN_DEFAULT);
            return this;
        }

        protected CellMutator (int row, int col)
        {
            _row = row;
            _col = col;
        }

        protected int _row, _col;
    }

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

    /**
     * Gets a mutator for the given cell.
     */
    public CellMutator cell (int row, int col)
    {
        return new CellMutator(row, col);
    }

    protected void valign (
        int row, int col, HasVerticalAlignment.VerticalAlignmentConstant align)
    {
        getFlexCellFormatter().setVerticalAlignment(row, col, align);
    }

    protected void halign (
        int row, int col, HasHorizontalAlignment.HorizontalAlignmentConstant align)
    {
        getFlexCellFormatter().setHorizontalAlignment(row, col, align);
    }
}
