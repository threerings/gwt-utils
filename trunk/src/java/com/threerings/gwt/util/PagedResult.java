//
// $Id$

package com.threerings.gwt.util;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Represents one page of a large data set.
 */
public class PagedResult<T>
    implements IsSerializable
{
    /** The total number of elements on the server. */
    public int total;

    /** The current page of elements */
    public List<T> page;
}
