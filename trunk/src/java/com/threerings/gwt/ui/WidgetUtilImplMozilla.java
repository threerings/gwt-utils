//
// $Id$

package com.threerings.gwt.ui;

/**
 * Provides Mozilla-specific widget-related utility functions.
 */
public class WidgetUtilImplMozilla extends WidgetUtilImpl
{
    @Override // from WidgetUtilImpl
    public String createFlashObjectDefinition (String ident, String movie, String width,
                                               String height, String flashVars, boolean transparent)
    {
        String params = "";
        if (flashVars != null) {
            params += "FlashVars=\"" + flashVars + "\" "; // trailing space
        }
        params += "bgcolor=\"#000000\" ";

        String tag = "<embed id=\"" + ident + "\" name=\"" + ident + "\" " +
            "type=\"application/x-shockwave-flash\" " +
            "pluginspage=\"http://www.macromedia.com/go/getflashplayer\" " +
            "wmode=\"" + (transparent ? "transparent" : "opaque") + "\"";
        if (width.length() > 0) {
            tag += " width=\"" + width + "\"";
        }
        if (height.length() > 0) {
            tag += " height=\"" + height + "\"";
        }
        tag += " src=\"" + movie + "\" allowScriptAccess=\"sameDomain\"" +
            " allowFullScreen=\"true\" " + params + "/>";
        return tag;
    }
}
