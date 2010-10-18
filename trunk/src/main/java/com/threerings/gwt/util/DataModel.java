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

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.threerings.gwt.ui.PagedGrid;

/**
 * An interface through which widgets such as {@link PagedGrid} may request paged server-side data
 * asynchronously.
 */
public interface DataModel<T>
{
    /**
     * Returns the total number of items in this model. It is OK if this returns zero until after
     * the first call to {@link #doFetchRows} is called back. You may also return -1 to indicate
     * that this model does not supply a row count, and the pager should continue offering pages
     * for as long as the underlying service is able to satisfy the requests.
     */
    int getItemCount ();

    /**
     * Perform a paged data request for the given number of items at the given offset into the
     * result set. The result is returned through the given callback.
     */
    void doFetchRows (int start, int count, AsyncCallback<List<T>> callback);

    /**
     * Called when we wish to remove an item from our locally cached data.
     */
    void removeItem (T item);
}
