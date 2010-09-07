//
// $Id$

package com.threerings.gwt.ui;

import java.util.List;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Widget;

/**
 * Displays a grid of UI elements in pages.
 */
public abstract class PagedGrid<T> extends PagedWidget<T>
{
    /**
     * Creates a grid of the specified dimensions (a pox on Google for asking for height, width
     * instead of width, height). The navigation will be placed on the top.
     */
    public PagedGrid (int rows, int columns)
    {
        this(rows, columns, NAV_ON_TOP);
    }

    /**
     * Creates a grid of the specified dimensions (a pox on Google for asking for height, width
     * instead of width, height).
     */
    public PagedGrid (int rows, int columns, int navLoc)
    {
        super(rows*columns, navLoc);

        _rows = rows;
        _cols = columns;

        setStyleName("pagedGrid");
    }

    /**
     * Configures the horizontal and vertical alignment of cells. This must be called before the
     * grid is displayed.
     */
    public void setCellAlignment (HasAlignment.HorizontalAlignmentConstant horiz,
                                  HasAlignment.VerticalAlignmentConstant vert)
    {
        _cellHorizAlign = horiz;
        _cellVertAlign = vert;
    }

    @Override
    protected Widget createContents (int start, int count, List<T> list)
    {
        int limit = padToFullPage() ? count : Math.min(list.size(), count);
        int cells = padToFullPage() ? count : limit;
        Grid grid = new Grid(Math.min((int)Math.ceil(cells / (float)_cols), _rows),
                             Math.min(_cols, cells));
        grid.setStyleName("Grid");
        grid.setCellPadding(0);
        grid.setCellSpacing(0);
        grid.setWidth("100%");

        for (int ii = 0; ii < limit; ii++) {
            int row = (ii / _cols), col = (ii % _cols);
            T item = ii >= list.size() ? null : list.get(ii);
            Widget widget = createWidget(item);
            if (widget != null) {
                grid.setWidget(row, col, widget);
            }
            formatCell(grid.getCellFormatter(), row, col, limit);
            formatCell(grid.getCellFormatter(), row, col, item);
        }

        return grid;
    }

    /**
     * Configures the formatting for a particular cell based on its location in the grid.
     */
    protected void formatCell (HTMLTable.CellFormatter formatter, int row, int col, int limit)
    {
        formatter.setHorizontalAlignment(row, col, _cellHorizAlign);
        formatter.setVerticalAlignment(row, col, _cellVertAlign);
        formatter.setStyleName(row, col, "Cell");
        if (row == (limit-1)/_cols) {
            formatter.addStyleName(row, col, "BottomCell");
        } else if (row == 0) {
            formatter.addStyleName(row, col, "TopCell");
        } else {
            formatter.addStyleName(row, col, "MiddleCell");
        }
    }

    /**
     * Configures the formatting for a particular cell based on its contents.
     */
    protected void formatCell (HTMLTable.CellFormatter formatter, int row, int col, T item)
    {
        // nothing by default
    }

    /**
     * If a derived class returns true here, a grid will always be filled to a full page and any
     * cells for which there are no data will result in a call to {@link #createWidget} with null
     * supplied as the item.
     */
    protected boolean padToFullPage ()
    {
        return false;
    }

    /** Create a widget to put in the grid. */
    protected abstract Widget createWidget (T item);

    protected HasAlignment.HorizontalAlignmentConstant _cellHorizAlign = HasAlignment.ALIGN_CENTER;
    protected HasAlignment.VerticalAlignmentConstant _cellVertAlign = HasAlignment.ALIGN_MIDDLE;

    protected int _rows, _cols;
}
