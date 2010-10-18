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
 * Provides IE6-specific widget-related utility functions.
 */
public class WidgetUtilImplIE6 extends WidgetUtilImpl
{
    @Override // from WidgetUtilImpl
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

        String tag = "<object id=\"" + obj.ident + "\"";
        if (obj.width.length() > 0) {
            tag += " width=\"" + obj.width + "\"";
        }
        if (obj.height.length() > 0) {
            tag += " height=\"" + obj.height + "\"";
        }
        tag += " classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\"" +
            " codebase=\"http://active.macromedia.com/flash7/cabs/" +
            "swflash.cab#version=" + WidgetUtil.FLASH_VERSION + "\"" +
            " allowScriptAccess=\"sameDomain\">" +
            params + "</object>";
        return tag;
    }

    @Override // from WidgetUtilImpl
    public HTML createApplet (String ident, String archive, String clazz,
                              String width, String height, boolean mayScript, String ptags)
    {
        String html = "<object classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" " +
            "width=\"" + width + "\" height=\"" + height + "\" " +
            "codebase=\"http://java.sun.com/update/1.5.0/" +
            "jinstall-1_5-windows-i586.cab#Version=5,0,0,5\">";
        if (mayScript) {
            html += "<param name=\"mayscript\" value=\"true\"/>";
        }
        html += "<param name=\"code\" value=\"" + clazz + "\"/>";
        html += "<param name=\"archive\" value=\"" + archive + "\"/>";
        html += ptags;
        html += "</object>";
        return new HTML(html);
    }
}
