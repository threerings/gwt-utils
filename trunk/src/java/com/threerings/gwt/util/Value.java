//
// $Id$

package com.threerings.gwt.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains a changing value and provides a mechanism for listeners to react to changes to the
 * value.
 */
public class Value<T>
{
    /** Used to hear about changes to the value. */
    public interface Listener<T> {
        /** Called when the value has changed. */
        public void valueChanged (T value);
    }

    /**
     * Creates a new value with the specified initial value.
     */
    public Value (T init)
    {
        _value = init;
    }

    /**
     * Adds a listener which will subsequently be notified when this value changes.
     */
    public void addListener (Listener<T> listener)
    {
        _listeners.add(listener);
    }

    /**
     * Removes a listener from this value.
     */
    public void removeListener (Listener<T> listener)
    {
        _listeners.remove(listener);
    }

    /**
     * Returns the current value.
     */
    public T get ()
    {
        return _value;
    }

    /**
     * Updates this value and notifies all listeners.
     */
    public void update (T value)
    {
        // store our new current value
        _value = value;

        // notify our listeners; we snapshot our listeners before iterating to avoid problems if
        // listeners add or remove themselves while we're dispatching this notification
        for (Listener<T> listener : new ArrayList<Listener<T>>(_listeners)) {
            listener.valueChanged(value);
        }
    }

    protected T _value;
    protected List<Listener<T>> _listeners = new ArrayList<Listener<T>>();
}
