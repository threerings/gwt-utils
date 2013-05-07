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

package com.threerings.gwt.util;

import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A data model that can be customized for components that obtain their data by calling a service
 * method to fetch a range of items.  Type T is from DataModel, and must be the type that is
 * return in a list from DataModel.doFetchRows.  Type R is for the AsyncCallback, and can be
 * anything - it is passed into getCount() and getRows() from the service call.
 */
public abstract class ServiceBackedDataModel<T, R> implements DataModel<T>
{
    /**
     * Prepends an item to an already loaded model. The model must have at least been asked to
     * display its first page (and hence have it's total count).
     */
    public void prependItem (T item)
    {
        // if we're on the first page, this new item will show up, so add it
        if (_pageOffset == 0) {
            _pageItems.add(0, item);
        }
        _count++;
    }

    /**
     * Appends an item to an already loaded model. The model must have at least been asked to
     * display its first page (and hence have it's total count).
     */
    public void appendItem (T item)
    {
        // if we're on the last page and there are fewer than a full page of items, this new item
        // will show up on this page so add it
        if (_pageItems.size() < _pageCount) {
            _pageItems.add(item);
        }
        _count++;
    }

    /**
     * Clears out any cached data and resets the model to total blankness.
     */
    public void reset ()
    {
        _count = -1;
        _pageOffset = 0;
        _pageCount = -1;
        _pageItems = Collections.emptyList();
    }

    @Override // from interface DataModel
    public int getItemCount ()
    {
        return Math.max(_count, _pageOffset + _pageItems.size());
    }

    @Override // from interface DataModel
    public void removeItem (T item)
    {
        _pageItems.remove(item);
        _count--;
    }

    @Override // from interface DataModel
    public void doFetchRows (int start, int count, final AsyncCallback<List<T>> callback)
    {
        // if we have data, and are requesting the same data we have...
        if ((_count >= 0) && (_pageOffset == start) && (_pageCount == count) &&
                // and we're either on the last page
                (((start + _pageCount > _count) && !_pageItems.isEmpty()) ||
                // or have enough items for the page requested..
                (_pageItems.size() == count))) {
            callback.onSuccess(_pageItems);

        } else {
            callFetchService(new PagedRequest(_pageOffset = start, _pageCount = count, _count < 0),
                    new AsyncCallback<R>() {
                public void onSuccess (R result) {
                    ServiceBackedDataModel.this.onSuccess(result, callback);
                }
                public void onFailure (Throwable cause) {
                    ServiceBackedDataModel.this.reportFailure(cause);
                }
            });
        }
    }

    protected void onSuccess (R result, AsyncCallback<List<T>> callback)
    {
        setCurrentResult(result);
        if (_count < 0) {
            _count = getCount(result);
        }
        _pageItems = getRows(result);
        callback.onSuccess(_pageItems);
    }

    /**
     * Lets subclasses know that the result has arrived for the current page. This is handy if the
     * getRows method needs to access a cross-section of the result such as a map of member names.
     */
    protected void setCurrentResult (R result)
    {
    }

    /**
     * Calls the service to obtain data. Implementations should make a service call using the
     * callback provided. If needCount is set, the implementation should also request the total
     * number of items from the server (this is normally done in the same call that requests a
     * page but may be optional for performance reasons). By default this implementation will
     * throw an exception.
     * NOTE: subclasses must override one of the two callFetchService methods
     */
    protected void callFetchService (
        int start, int count, boolean needCount, AsyncCallback<R> callback)
    {
       throw new UnsupportedOperationException();
    }

    /**
     * Calls the service to obtain data. Implementations should make a service call using the
     * callback provided. If needCount is set, the implementation should also request the total
     * number of items from the server (this is normally done in the same call that requests a
     * page but may be optional for performance reasons). By default, this calls
     * {@link #callFetchService(int, int, boolean, AsyncCallback)} method with the
     * {@code requests}'s members.
     * NOTE: subclasses must override one of the two callFetchService methods
     */
    protected void callFetchService (PagedRequest request, AsyncCallback<R> callback)
    {
        callFetchService(request.offset, request.count, request.needCount, callback);
    }

    /**
     * Returns the count from the service result.
     */
    protected abstract int getCount (R result);

    /**
     * Returns the list of row items from the service result.
     */
    protected abstract List<T> getRows (R result);

    /**
     * Reports an error from the server while processing the service call. Normally this should
     * delegate to an application method that can interpret or translate the exception message
     * and then show a popup.
     */
    protected abstract void reportFailure (Throwable caught);

    /** The count of items in our model, filled in by the first call to {@link #doFetchRows}. */
    protected int _count = -1;

    /** The offset of the page we're currently displaying. */
    protected int _pageOffset;

    /** The requested count for the page we're currently displaying. */
    protected int _pageCount = -1;

    /** The items we got back for the page we're currently displaying. */
    protected List<T> _pageItems = Collections.emptyList();
}
