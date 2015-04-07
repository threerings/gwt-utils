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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.util.DataModel;

/**
 * Displays a paginated collection of UI elements.
 */
public abstract class PagedWidget<T> extends FlowPanel
{
    public static final int NAV_ON_TOP = 0;
    public static final int NAV_ON_BOTTOM = 1;
    // public static final int NAV_ON_BOTH = 2; // not yet supported

    public PagedWidget (int resultsPerPage)
    {
        this(resultsPerPage, NAV_ON_TOP);
    }

    public PagedWidget (int resultsPerPage, int navLoc)
    {
        //setHorizontalAlignment(ALIGN_LEFT);
        _resultsPerPage = resultsPerPage;
        _navLoc = navLoc;

        // these will be used for navigation
        _next = new Button("Next");
        _next.setStyleName("Button");
        _next.addStyleName("NextButton");
        _next.addClickHandler(new ClickHandler() {
            public void onClick (ClickEvent event) {
                displayPageFromClick(_page+1);
            }
        });
        _prev = new Button("Prev");
        _prev.setStyleName("Button");
        _prev.addStyleName("PrevButton");
        _prev.addClickHandler(new ClickHandler() {
            public void onClick (ClickEvent event) {
                displayPageFromClick(_page-1);
            }
        });

        _controls = new SmartTable(0, 0);
        _controls.setWidth("100%");

        addCustomControls(_controls);
        _infoCol = (_controls.getRowCount() == 0) ? 0 : _controls.getCellCount(0);
        _controls.getFlexCellFormatter().setStyleName(0, _infoCol, "Info");
        _controls.setWidget(0, _infoCol+1, _prev, 1, "Prev");
        _controls.setWidget(0, _infoCol+2, _next, 1, "Next");

        if (navLoc == NAV_ON_TOP) {
            _controls.setStyleName("Header");
            _controls.insertCell(0, 0);
            _infoCol++;
            _controls.getFlexCellFormatter().setStyleName(0, 0, "TopLeft");
            _controls.getFlexCellFormatter().setStyleName(0, _controls.getCellCount(0), "TopRight");
        } else {
            _controls.setStyleName("BareFooter");
        }

        // show initial controls
        add(_controls);
    }

    /**
     * Returns the page we're currently displaying.
     */
    public int getPage ()
    {
        return _page;
    }

    /**
     * Returns the index in the complete list of the first element we're currently displaying.
     */
    public int getOffset ()
    {
        return _page * _resultsPerPage;
    }

    /**
     * Returns true if this panel is configured with its model.
     */
    public boolean hasModel ()
    {
        return _model != null;
    }

    /**
     * Configures this panel with a {@link DataModel} and kicks the data
     * retrieval off by requesting the specified page to be displayed.
     */
    public void setModel (DataModel<T> model, int page)
    {
        _model = model;
        displayPage(page, true);
    }

    /**
     * Returns the model in use by this panel or null if we have no model.
     */
    public DataModel<T> getModel ()
    {
        return _model;
    }

    /**
     * Returns the controls in use by this panel or null if we have no model.
     */
    public SmartTable getControls ()
    {
        return _controls;
    }

    public void reloadPage ()
    {
        displayPage(getPage(), true);
    }

    /**
     * Displays the specified page. Does nothing if we are already displaying
     * that page unless forceRefresh is true.
     */
    public void displayPage (final int page, boolean forceRefresh)
    {
        if (_page == page && !forceRefresh) {
            return; // NOOP!
        }

        // Display the now loading widget, if necessary.
        configureLoadingNavi(_controls, 0, _infoCol);

        _page = Math.max(page, 0);

        final boolean overQuery = (_model.getItemCount() < 0);
        final int count = _resultsPerPage;
        final int start = _resultsPerPage * page;
        _model.doFetchRows(start, overQuery ? (count + 1) : count, new AsyncCallback<List<T>>() {
            public void onSuccess (List<T> result) {
                if (overQuery) {
                    // if we requested 1 item too many, see if we got it
                    if (result.size() < (count + 1)) {
                        // no: this is the last batch of items, woohoo
                        _lastItem = start + result.size();
                    } else {
                        // yes: strip it before anybody else gets to see it
                        result.remove(count);
                        _lastItem = -1;
                    }
                } else {
                    // a valid item count should be available at this point
                    _lastItem = _model.getItemCount();
                }
                displayResults(start, count, result);
            }
            public void onFailure (Throwable caught) {
                reportFailure(caught);
            }
        });
    }

    /**
     * Removes the specified item from the panel. If the item is currently being displayed, its
     * interface element will be removed as well.
     */
    public void removeItem (T item)
    {
        if (_model == null) {
            return; // if we have no model, stop here
        }
        // remove the item from our data model
        _model.removeItem(item);
        // force a relayout of this page
        displayPage(_page, true);
    }

    protected void configureLoadingNavi (FlexTable controls, int row, int col)
    {
        Widget widget = getNowLoadingWidget();
        if (widget != null) {
            controls.setWidget(row, col, widget);
            controls.getFlexCellFormatter().setHorizontalAlignment(row, col,
                HasAlignment.ALIGN_CENTER);
        }
        _next.setEnabled(false);
        _prev.setEnabled(false);
    }

    /**
     * Get the text that should be shown in the header bar between the "prev" and "next" buttons.
     */
    protected void configureNavi (FlexTable controls, int row, int col,
                                  int start, int limit, int total)
    {
        HorizontalPanel panel = new HorizontalPanel();
        panel.add(new Label("Page:")); // TODO: i18n

        // determine which pages we want to show
        int page = 1+start/_resultsPerPage;

        int pages;
        if (total < 0) {
            pages = page + 1;
        } else {
            pages = total/_resultsPerPage + (total%_resultsPerPage == 0 ? 0 : 1);
        }

        List<Integer> shown = new ArrayList<Integer>();
        shown.add(1);
        for (int ii = Math.max(2, Math.min(page-2, pages-5));
             ii <= Math.min(pages-1, Math.max(page+2, 6)); ii++) {
            shown.add(ii);
        }
        if (pages != 1) {
            shown.add(pages);
        }

        // now display those as "Page: _1_ ... _3_ _4_ 5 _6_ _7_ ... _10_"
        int ppage = 0;
        for (Integer cpage : shown) {
            if (cpage - ppage > 1) {
                panel.add(createPageLabel("...", null));
            }
            if (cpage == page) {
                panel.add(createPageLabel(""+page, "Current"));
            } else {
                panel.add(createPageLinkLabel(cpage));
            }
            ppage = cpage;
        }
        if (total < 0) {
            panel.add(createPageLabel("...", null));
        }
        controls.setWidget(row, col, panel);
        controls.getFlexCellFormatter().setHorizontalAlignment(row, col, HasAlignment.ALIGN_CENTER);
    }

    /**
     * Return true if the navigation should appear. The default only shows the navigation if there
     * is at least one item.
     */
    protected boolean displayNavi (int items)
    {
        return (items != 0);
    }

    protected Label createPageLinkLabel (final int page)
    {
        Label label = createPageLabel(""+page, "Link");
        label.addClickHandler(new ClickHandler() {
            public void onClick (ClickEvent event) {
                displayPageFromClick(page-1);
            }
        });
        return label;
    }

    protected Label createPageLabel (String text, String optStyle)
    {
        Label label = new Label(text);
        label.setStyleName("Page");
        if (optStyle != null) {
            label.addStyleName(optStyle);
        }
        return label;
    }

    protected void displayResults (int start, int count, List<T> list)
    {
        clear();

        if (_navLoc != NAV_ON_BOTTOM && displayNavi(_lastItem)) {
            add(_controls);
        }

        add((list.size() == 0) ? createEmptyContents() : createContents(start, count, list));

        if (_navLoc != NAV_ON_TOP && displayNavi(_lastItem)) {
            add(_controls);
        } else {
            Widget cap = new Label("");
            cap.addStyleName("EndCap");
            add(cap);
        }

        _prev.setEnabled(start > 0);
        _next.setEnabled(_lastItem < 0 || start + count < _lastItem);
        if (_lastItem != 0) {
            int shown = Math.max(list.size(), count);
            configureNavi(_controls, 0, _infoCol, start, shown, _lastItem);
        } else {
            _controls.setHTML(0, _infoCol, "&nbsp;");
        }
    }

    protected abstract Widget createContents (int start, int count, List<T> list);

    /**
     * Called when the user clicks a forward or back button.
     */
    protected void displayPageFromClick (int page)
    {
        displayPage(page, false);
    }

    protected Widget createEmptyContents ()
    {
        Label label = new Label(getEmptyMessage());
        label.setStyleName("Empty");
        return label;
    }

    protected void addCustomControls (FlexTable controls)
    {
    }

    /**
     * Returns the widget that should be displayed in place of the page list when navigating
     * between pages.  By default this returns null, indicating this should not happen; subclasses
     * can override this to display the widget.
     */
    protected Widget getNowLoadingWidget ()
    {
        return null;
    }

    /**
     * Report a service failure.
     */
    protected void reportFailure (Throwable caught)
    {
        java.util.logging.Logger.getLogger("PagedWidget").warning("Failure to page: " + caught);
    }

    protected abstract String getEmptyMessage ();

    protected int _navLoc;
    protected SmartTable _controls;
    protected int _infoCol;
    protected Button _next, _prev;

    protected DataModel<T> _model;
    protected int _lastItem;
    protected int _page;
    protected int _resultsPerPage;
}
