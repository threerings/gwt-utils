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

package com.threerings.gwt.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.ListBox;

/**
 * A list box that maintains a list of actual Java objects.
 */
public class ItemListBox<T> extends ListBox
{
    /**
     * Simple builder that allows multiple items to be added to the list box during construction.
     * @param <T> the type of item the list box holds
     * @param <Box> the type of item list box
     */
    public static class Builder<T, Box extends ItemListBox<T>>
    {
        /** The box we are building. */
        final public Box box;

        /**
         * Creates a new builder for the given list box.
         */
        public Builder (Box box)
        {
            this.box = box;
        }

        /**
         * Adds a new item to the list box.
         */
        public Builder<T, Box> add (T item)
        {
            box.addItem(item);
            return this;
        }

        /**
         * Adds a new item to the list box with the supplied label.
         */
        public Builder<T, Box> add (T item, String label)
        {
            box.addItem(item, label);
            return this;
        }

        /**
         * Causes the created list box to have the given item selected.
         */
        public Builder<T, Box> select (T item)
        {
            _select = item;
            return this;
        }

        /**
         * Returns the list box.
         */
        public Box build ()
        {
            if (_select != null) {
                box.setSelectedItem(_select);
            }
            return box;
        }

        protected T _select;
    }

    /**
     * Creates a new builder for an ItemListBox.
     */
    public static <T> Builder<T, ItemListBox<T>> builder ()
    {
        return new Builder<T, ItemListBox<T>>(new ItemListBox<T>());
    }

    /**
     * Creates an empty item list box.
     */
    public ItemListBox ()
    {
    }

    /**
     * Creates a list box with the supplied set of initial items.
     */
    public ItemListBox (Iterable<T> items)
    {
        for (T item : items) {
            addItem(item);
        }
    }

    /**
     * Adds the supplied item to this list box at the end of the list, using the supplied label
     * if not null. If no label is given, {@link #toLabel(Object)} is used to calculate it.
     */
    public void addItem (T item, String label)
    {
        addItem(label == null ? toLabel(item) : label);
        _items.add(item);
    }

    /**
     * Adds the supplied item to this list box at the end of the list.
     */
    public void addItem (T item)
    {
        addItem(item, null);
    }

    /**
     * Inserts the supplied item into this list box at the specified position, using the specified
     * label if given. If no label is given, {@link #toLabel(Object)} is used to calculate it.
     */
    public void insertItem (T item, int index, String label)
    {
        insertItem(label == null ? toLabel(item) : label, index);
        _items.add(index, item);
    }

    /**
     * Inserts the supplied item into this list box at the specified position.
     */
    public void insertItem (T item, int index)
    {
        insertItem(item, index, null);
    }

    /**
     * Removes the supplied item from this list box, returning true if the item was found.
     */
    public boolean removeItem (T item)
    {
        int index = _items.indexOf(item);
        if (index == -1) {
            return false;
        }
        _items.remove(index);
        removeItem(index);
        return true;
    }

    /**
     * Returns the currently selected item, or null if no item is selected.
     */
    public T getSelectedItem ()
    {
        int selidx = getSelectedIndex();
        return (selidx >= 0) ? _items.get(selidx) : null;
    }

    /**
     * Selects the specified item.
     */
    public void setSelectedItem (T value)
    {
        setSelectedIndex(_items.indexOf(value));
    }

    @Override
    public void clear ()
    {
        super.clear();
        _items.clear();
    }

    // @Override // from ListBox
    // public void addItem (String item)
    // {
    //     throw new UnsupportedOperationException();
    // }

    // @Override // from ListBox
    // public void addItem (String item, String value)
    // {
    //     throw new UnsupportedOperationException();
    // }

    // @Override // from ListBox
    // public void insertItem (String item, int index)
    // {
    //     throw new UnsupportedOperationException();
    // }

    // @Override // from ListBox
    // public void insertItem (String item, String value, int index)
    // {
    //     throw new UnsupportedOperationException();
    // }

    /**
     * Returns the label text to display for the specified item. Defaults to calling {@link
     * String#valueOf} on the item.
     */
    protected String toLabel (T item)
    {
        return String.valueOf(item);
    }

    protected List<T> _items = new ArrayList<T>();
}
