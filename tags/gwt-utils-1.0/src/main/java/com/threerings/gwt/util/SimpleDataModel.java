//
// $Id$

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
