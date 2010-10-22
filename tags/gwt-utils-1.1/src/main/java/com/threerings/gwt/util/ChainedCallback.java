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

import com.google.common.base.Function;
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
            @Override public void onSuccess (T result) {
                forwardSuccess(f.apply(result));
            }
        };
    }

    /**
     * Creates a chained callback that calls the supplied pre-operation before passing the result
     * on to the supplied target callback.
     */
    public static <T> ChainedCallback<T, T> before (AsyncCallback<T> target,
                                                    final Function<T, Void> preOp)
    {
        return new ChainedCallback<T, T>(target) {
            @Override public void onSuccess (T result) {
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
