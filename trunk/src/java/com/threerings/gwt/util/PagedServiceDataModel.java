//
// $Id$

package com.threerings.gwt.util;

import java.util.List;

public abstract class PagedServiceDataModel<T, R extends PagedResult<T>>
    extends ServiceBackedDataModel<T, R>
{
    @Override
    protected int getCount (R result)
    {
        return result.total;
    }

    @Override
    protected List<T> getRows (R result)
    {
        return result.page;
    }
}
