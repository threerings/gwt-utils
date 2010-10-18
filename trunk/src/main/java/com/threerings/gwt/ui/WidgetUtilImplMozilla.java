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

/**
 * Provides Mozilla-specific widget-related utility functions.
 */
public class WidgetUtilImplMozilla extends WidgetUtilImpl
{
    @Override // from WidgetUtilImpl
    public String createDefinition (WidgetUtil.FlashObject obj)
    {
        String params = "";
        if (obj.flashVars != null) {
            params += "FlashVars=\"" + obj.flashVars + "\" "; // trailing space
        }
        params += "bgcolor=\"" + obj.bgcolor + "\" ";

        String tag = "<embed id=\"" + obj.ident + "\" name=\"" + obj.ident + "\" " +
            "type=\"application/x-shockwave-flash\" " +
            "pluginspage=\"http://www.macromedia.com/go/getflashplayer\" " +
            "wmode=\"" + (obj.transparent ? "transparent" : "opaque") + "\"";
        if (obj.width.length() > 0) {
            tag += " width=\"" + obj.width + "\"";
        }
        if (obj.height.length() > 0) {
            tag += " height=\"" + obj.height + "\"";
        }
        tag += " src=\"" + obj.movie + "\" allowScriptAccess=\"sameDomain\"" +
            " allowFullScreen=\"true\" " + params + "/>";
        return tag;
    }
}
