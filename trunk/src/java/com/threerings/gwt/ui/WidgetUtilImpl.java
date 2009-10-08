//
// $Id$

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
