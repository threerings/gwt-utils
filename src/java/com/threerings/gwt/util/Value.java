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
    public static <T> Value<T> create (T value)
    {
        return new Value<T>(value);
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
     * Adds a listener and immediately triggers it with our current value.
     */
    public void addListenerAndTrigger (Listener<T> listener)
    {
        addListener(listener);
        listener.valueChanged(get());
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

    /**
     * Creates a value that maps this value via a function. Every time the target value is updated
     * the mapped value will be updated, regardless of whether or not the mapped value differs. The
     * returned value will be a view and reject attempts to call {@link #update}.
     */
    public <M> Value<M> map (Function<T, M> func)
    {
        return new MappedValue<T, M>(this, func);
    }

    /** Used by {@link #map}. */
    protected static class MappedValue<A, B> extends Value<B> implements Listener<A>
    {
        public MappedValue (Value<A> value, Function<A, B> func) {
            super(func.apply(value.get()));
            _func = func;
            value.addListener(this);
        }

        @Override // from Value<B>
        public void update (B value) {
            throw new UnsupportedOperationException();
        }

        // from interface Value.Listener<A>
        public void valueChanged (A value) {
            super.update(_func.apply(value));
        }

        protected Function<A, B> _func;
    }

    protected T _value;
    protected List<Listener<T>> _listeners = new ArrayList<Listener<T>>();
}
