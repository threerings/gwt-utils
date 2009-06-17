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
    public HTML createFlashContainer (String ident, String movie, String width,
                                      String height, String flashVars, boolean transparent)
    {
        return new HTML(
            createFlashObjectDefinition(ident, movie, width, height, flashVars, transparent));
    }

    /**
     * Creates the HTML string definition of an embedded Flash object.
     */
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

        String tag = "<object id=\"" + ident + "\" type=\"application/x-shockwave-flash\"";
        if (width.length() > 0) {
            tag += " width=\"" + width + "\"";
        }
        if (height.length() > 0) {
            tag += " height=\"" + height + "\"";
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
