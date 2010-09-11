//
// $Id$

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
