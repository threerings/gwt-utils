//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Chains failure through to another callback and allows the chainer to handle success (possibly by
 * transforming the result and passing it through to the caller).
 */
public abstract class ChainedCallback<T, P> implements AsyncCallback<T>
{
    /**
     * Creates a chained callback that maps the result using the supplied function and passes the
     * mapped result to the target callback.
     */
    public static <T, P> ChainedCallback<T, P> map (AsyncCallback<P> target, final Function<T, P> f)
    {
        return new ChainedCallback<T, P>(target) {
            public void onSuccess (T result) {
                forwardSuccess(f.apply(result));
            }
        };
    }

    /**
     * Creates a chained callback that calls the supplied pre-operation before passing the result
     * on to the supplied target callback.
     */
    public static <T> ChainedCallback<T, T> wrap (AsyncCallback<T> target,
                                                  final Function<T, Void> preOp)
    {
        return new ChainedCallback<T, T>(target) {
            public void onSuccess (T result) {
                preOp.apply(result);
                forwardSuccess(result);
            }
        };
    }

    /**
     * Creates a chained callback with the supplied target.
     */
    public ChainedCallback (AsyncCallback<P> target)
    {
        _target = target;
    }

    // from interface AsyncCallback
    public abstract void onSuccess (T result);

    // from interface AsyncCallback
    public void onFailure (Throwable cause)
    {
        _target.onFailure(cause);
    }

    protected void forwardSuccess (P result)
    {
        _target.onSuccess(result);
    }

    protected AsyncCallback<P> _target;
}
