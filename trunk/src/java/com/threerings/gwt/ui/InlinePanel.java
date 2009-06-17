//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A flow panel that applies the "inline" style and a bit of space to every widget added to it.
 * Good for making text bits flow horizontally eg [item name] [by username], but bad for images.
 */
public class InlinePanel extends FlowPanel
{
    public InlinePanel (String styleName)
    {
        super();
        if (styleName != null) {
            setStyleName(styleName);
        }
    }

    @Override public void add (Widget widget)
    {
        widget.addStyleName("inline" + ((getWidgetCount() > 0) ? "L" : ""));
        super.add(widget);
    }

    @Override public void insert (Widget widget, int beforeIndex)
    {
        if (beforeIndex == 0) {
            if (getWidgetCount() > 0) {
                getWidget(0).removeStyleName("inline");
                getWidget(0).addStyleName("inlineL");
            }
            widget.addStyleName("inline");
        } else {
            widget.addStyleName("inlineL");
        }
        super.insert(widget, beforeIndex);
    }

    @Override public boolean remove (Widget widget)
    {
        int oindex = getWidgetIndex(widget);
        if (super.remove(widget)) {
            if (oindex == 0 && getWidgetCount() > 0) {
                getWidget(0).removeStyleName("inlineL");
                getWidget(0).addStyleName("inline");
            }
            return true;
        }
        return false;
    }
}
