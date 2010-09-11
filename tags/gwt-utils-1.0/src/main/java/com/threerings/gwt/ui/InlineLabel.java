//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.Label;

/**
 * A small helper class to provide us with an inline label, since the default
 * implementation of Label creates a DIV, and there's no way to make a SPAN.
 */
public class InlineLabel
    extends Label
{
    public InlineLabel ()
    {
        this("");
    }

    public InlineLabel (String text)
    {
        this(text, true, false, false);
    }

    public InlineLabel (String text, boolean wordWrap, boolean leftPad, boolean rightPad)
    {
        super(text, wordWrap);
        setStyleName("inline" + (leftPad ? "L" : "") + (rightPad ? "R" : ""));
    }
}

