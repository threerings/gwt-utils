//
// $Id$

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
     * Adds the supplied item to this list box at the end of the list.
     */
    public void addItem (T item)
    {
        super.addItem(toLabel(item));
        _items.add(item);
    }

    /**
     * Inserts the supplied item into this list box at the specified position.
     */
    public void insertItem (T item, int index)
    {
        super.insertItem(toLabel(item), index);
        _items.add(index, item);
    }

    /**
     * Returns the currently selected item, or null if no item is selected.
     */
    public T getSelectedItem ()
    {
        int selidx = getSelectedIndex();
        return (selidx >= 0) ? _items.get(selidx) : null;
    }

    @Override // from ListBox
    public void addItem (String item)
    {
        throw new UnsupportedOperationException();
    }

    @Override // from ListBox
    public void addItem (String item, String value)
    {
        throw new UnsupportedOperationException();
    }

    @Override // from ListBox
    public void insertItem (String item, int index)
    {
        throw new UnsupportedOperationException();
    }

    @Override // from ListBox
    public void insertItem (String item, String value, int index)
    {
        throw new UnsupportedOperationException();
    }

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
