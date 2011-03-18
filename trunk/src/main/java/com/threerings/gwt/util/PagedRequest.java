//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Indicates a page of some data for which a {@link PagedResult} is provided by the service,
 * including a {@code needCount} value.
 * @see PagedResult
 * @see PagedServiceDataModel
 * @see ServiceBackedDataModel#callFetchService()
 */
public class PagedRequest implements IsSerializable
{
    /** The offset of the first item in the requested page within the whole data set. */
    public int offset;

    /** The number of items requested for the page. */
    public int count;

    /** Whether or not the count is required (i.e. this is the first service call). This is an
     * important optimization that avoids two round trips to the server. */
    public boolean needCount;

    /**
     * Creates a new paged request with the given values.
     */
    public PagedRequest (int offset, int count, boolean needCount)
    {
        this.offset = 0;
        this.count = count;
        this.needCount = needCount;
    }

    /**
     * Creates an empty paged request for deserialization.
     */
    public PagedRequest ()
    {
    }
}
