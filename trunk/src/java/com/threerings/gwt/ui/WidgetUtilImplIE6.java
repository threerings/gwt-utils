//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.HTML;

/**
 * Provides IE6-specific widget-related utility functions.
 */
public class WidgetUtilImplIE6 extends WidgetUtilImpl
{
    @Override // from WidgetUtilImpl
    public String createFlashObjectDefinition (String ident, String movie, String width,
                                               String height, String flashVars, boolean transparent)
    {
        String params = "<param name=\"movie\" value=\"" + movie + "\">" +
            "<param name=\"allowFullScreen\" value=\"true\">" +
            "<param name=\"wmode\" value=\"" + (transparent ? "transparent" : "opaque") + "\">" +
            "<param name=\"bgcolor\" value=\"#000000\">";
        if (flashVars != null) {
            params += "<param name=\"FlashVars\" value=\"" + flashVars + "\">";
        }

        String tag = "<object id=\"" + ident + "\"";
        if (width.length() > 0) {
            tag += " width=\"" + width + "\"";
        }
        if (height.length() > 0) {
            tag += " height=\"" + height + "\"";
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
