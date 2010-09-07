//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A flow panel that sets all children to have the "position: absolute" inline style as they are
 * added. This is sort of like gwt's {@link com.google.gwt.user.client.ui.AbsolutePanel}, but
 * lighter weight:<ol>
 *
 * <li>It does not assume the positioning context created should always be "relative". Relative
 * coordinates are diffcult to tune since they depened on the positions of previous elements which
 * are most likely offset in some way.</li>
 *
 * <li>It does not provide methods to set the left and top in code. It's easier to do these in css
 * where all the width and heights are normally defined.</li></ol>
 */
public class AbsoluteCSSPanel extends FlowPanel
{
    /**
     * Constructs a new panel with the given style class and no inline position style. The position
     * should in this case be defined in the css.
     */
    public AbsoluteCSSPanel (String styleName)
    {
        setStyleName(styleName);
    }

    /**
     * Constructs a new panel with the given style class and the given inline position style.
     */
    public AbsoluteCSSPanel (String styleName, String position)
    {
        setStyleName(styleName);
        DOM.setStyleAttribute(getElement(), "position", position);
    }

    @Override // from FlowPanel
    public void add (Widget w)
    {
        super.add(w);
        DOM.setStyleAttribute(w.getElement(), "position", "absolute");
    }
}
