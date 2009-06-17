//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.DOM;

import com.google.gwt.user.client.ui.Widget;

/**
 * A form submit button.
 */
public class SubmitField extends Widget
{
    public SubmitField (String name, String value)
    {
        setElement(DOM.createElement("input"));
        DOM.setElementProperty(getElement(), "type", "submit");
        DOM.setElementProperty(getElement(), "value", value);
        DOM.setElementProperty(getElement(), "name", name);
    }
}
