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

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
//import com.google.common.base.Predicates;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * A data model that is implements {@link DataModel} with a {@link List} of items.
 */
public class SimpleDataModel<T>
    implements DataModel<T>
{
    /**
     * Creates a new simple data model with the supplied list of items.
     */
    public static <T> SimpleDataModel<T> newModel (List<T> items)
    {
        return new SimpleDataModel<T>(items);
    }

    /**
     * Construct a new {@link SimpleDataModel} instance with the given items.
     */
    public SimpleDataModel (List<T> items)
    {
        _items = items;
    }

    /**
     * Returns a data model that contains only items that match the supplied predicate.
     */
    public SimpleDataModel<T> filter (Predicate<T> pred)
    {
//        if (Predicates.alwaysTrue().equals(pred)) {
//            return this; // optimization
//        }
        List<T> items = new ArrayList<T>();
        for (T item : _items) {
            if (pred.apply(item)) {
                items.add(item);
            }
        }
        return createFilteredModel(items);
    }

    /**
     * Adds an item to this model. Does not force the refresh of any display.
     */
    public void addItem (int index, T item)
    {
        if (_items == null) {
            return;
        }
        _items.add(index, item);
    }

    /**
     * Updates the specified item if found in the model, prepends it otherwise.
     */
    public void updateItem (T item)
    {
        if (_items == null) {
            return;
        }
        int idx = _items.indexOf(item);
        if (idx == -1) {
            _items.add(0, item);
        } else {
            _items.set(idx, item);
        }
    }

    /**
     * Returns the first item that matches the supplied predicate or null if no items in this model
     * matches the predicate.
     */
    public T findItem (Predicate<T> p)
    {
        if (_items == null) {
            return null;
        }
        for (T item : _items) {
            if (p.apply(item)) {
                return item;
            }
        }
        return null;
    }

    // from DataModel
    public int getItemCount ()
    {
        return (_items != null) ? _items.size() : 0;
    }

    // from DataModel
    public void doFetchRows (int start, int count, AsyncCallback<List<T>> callback)
    {
        List<T> subList = new ArrayList<T>();
        int limit = Math.min(count, _items.size()-start);
        for (int ii = 0; ii < limit; ii ++) {
            subList.add(_items.get(start + ii));
        }
        callback.onSuccess(subList);
    }

    // from DataModel
    public void removeItem (T item)
    {
        if (_items != null) {
            _items.remove(item);
        }
    }

    /**
     * Creates a filtered model using the supplied set of items. Subclasses of SimpleDataModel may
     * wish to return a subclass of SimpleDataModel themselves when filtered.
     */
    protected SimpleDataModel<T> createFilteredModel (List<T> items)
    {
        return new SimpleDataModel<T>(items);
    }

    /** The list of items we serve through the {@link DataModel} interface. */
    protected List<T> _items;
}
