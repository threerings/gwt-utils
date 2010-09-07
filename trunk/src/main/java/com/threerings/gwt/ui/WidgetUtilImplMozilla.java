//
// $Id$

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
