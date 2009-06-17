//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A panel that lines up it's contents horizontally and wraps them around at the end of lines.
 * Child widgets will be assigned the style attribute "float: left" when added and it will be
 * removed when they are. This is a good div and css using alternative to HorizontalPanel.
 */
public class FloatPanel extends FlowPanel
{
    public FloatPanel (String styleName)
    {
        super();
        if (styleName != null) {
            setStyleName(styleName);
        }

        SimplePanel clear = new SimplePanel();
        clear.setStyleName("fpClear");
        super.add(clear);
    }

    @Override public void add (Widget widget)
    {
        // insert it at the end, before the clear box
        insert(widget, this.getWidgetCount() - 1);
    }

    @Override public void insert (Widget widget, int beforeIndex)
    {
        widget.addStyleName("fpFloatLeft");
        super.insert(widget, beforeIndex);
    }

    @Override public boolean remove (Widget widget)
    {
        if (super.remove(widget)) {
            widget.removeStyleName("fpFloatLeft");
            return true;
        }
        return false;
    }
}
