//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.Label;

import com.threerings.gwt.util.Value;

/**
 * Displays a dynamically changing value. Automatically handles regenerating its contents when the
 * value changes and registers and clears its listenership when it is added to and removed from the
 * DOM.
 */
public class ValueLabel<T> extends Label
    implements Value.Listener<T>
{
    /**
     * Creates a value label for the supplied value with the specified CSS styles.
     */
    public static <T> ValueLabel<T> create (Value<T> value, String... styles)
    {
        return new ValueLabel<T>(value, styles);
    }

    /**
     * Creates a value label with the supplied value and CSS styles.
     */
    public ValueLabel (Value<T> value, String... styles)
    {
        _value = value;
        Widgets.setStyleNames(this, styles);
    }

    // from Value.Listener<T>
    public void valueChanged (T value)
    {
        setText(getText(value));
    }

    @Override // from Widget
    public void onLoad ()
    {
        super.onLoad();
        _value.addListener(this);
        valueChanged(_value.get());
    }

    @Override // from Widget
    public void onUnload ()
    {
        super.onLoad();
        _value.removeListener(this);
    }

    /**
     * Called to generate our text when the value changes. The default implementation simply
     * converts the value to a string via {@link String#valueOf}.
     */
    protected String getText (T value)
    {
        return String.valueOf(value);
    }

    /** The value we're displaying. */
    protected Value<T> _value;
}
