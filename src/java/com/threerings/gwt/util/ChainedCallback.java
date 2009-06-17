//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Chains failure through to another callback and allows the chainer to handle success (possibly by
 * transforming the result and passing it through to the caller).
 */
public abstract class ChainedCallback<T,P> implements AsyncCallback<T>
{
    public ChainedCallback (AsyncCallback<P> parent)
    {
        _parent = parent;
    }

    // from interface AsyncCallback
    public abstract void onSuccess (T result);

    // from interface AsyncCallback
    public void onFailure (Throwable cause)
    {
        _parent.onFailure(cause);
    }

    protected AsyncCallback<P> _parent;
}
