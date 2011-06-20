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
