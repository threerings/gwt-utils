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

import com.google.gwt.user.client.ui.HTML;

/**
 * Provides browser-specific widget-related utility functions.
 */
public class WidgetUtilImpl
{
    /**
     * Creates the HTML needed to display a Flash movie.
     */
    public HTML createContainer (WidgetUtil.FlashObject obj)
    {
        return new HTML(createDefinition(obj));
    }

    /**
     * Creates the HTML string definition of an embedded Flash object.
     */
    public String createDefinition (WidgetUtil.FlashObject obj)
    {
        String transparent = obj.transparent ? "transparent" : "opaque";
        String params = "<param name=\"movie\" value=\"" + obj.movie + "\">" +
            "<param name=\"allowFullScreen\" value=\"true\">" +
            "<param name=\"wmode\" value=\"" + transparent + "\">" +
            "<param name=\"bgcolor\" value=\"" + obj.bgcolor + "\">";
        if (obj.flashVars != null) {
            params += "<param name=\"FlashVars\" value=\"" + obj.flashVars + "\">";
        }

        String tag = "<object id=\"" + obj.ident + "\" type=\"application/x-shockwave-flash\"";
        if (obj.width.length() > 0) {
            tag += " width=\"" + obj.width + "\"";
        }
        if (obj.height.length() > 0) {
            tag += " height=\"" + obj.height + "\"";
        }
        tag += " allowFullScreen=\"true\" allowScriptAccess=\"sameDomain\">" + params + "</object>";
        return tag;
    }

    /**
     * Creates the HTML needed to display a Java applet.
     */
    public HTML createApplet (String ident, String archive, String clazz,
                              String width, String height, boolean mayScript, String ptags)
    {
        String html = "<object classid=\"java:" + clazz + ".class\" " +
            "type=\"application/x-java-applet\" archive=\"" + archive + "\" " +
            "width=\"" + width + "\" height=\"" + height + "\">";
        if (mayScript) {
            html += "<param name=\"mayscript\" value=\"true\"/>";
        }
        html += ptags;
        html += "</object>";
        return new HTML(html);
    }
}
