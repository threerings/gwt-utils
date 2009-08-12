//
// $Id$

package com.threerings.gwt.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods related to the JavaScript "window".
 */
public class WindowUtil
{
    /**
     * Returns the query parameters tacked onto our URL as a mapping from key to value. Parameters
     * that have no value will be mapped to "true". For example: ?foo=bar&biff=baz&boink will be
     * returned in a map with { foo -> bar, biff -> baz, boink -> true }.
     */
    public static Map<String, String> getQueryParams ()
    {
        Map<String, String> params = new HashMap<String, String>();
        String search = getSearchString();
        search = search.substring(search.indexOf("?")+1);
        String[] bits = search.split("&");
        for (String bit : bits) {
            int eqidx = bit.indexOf("=");
            if (eqidx > 0) {
                params.put(bit.substring(0, eqidx), bit.substring(eqidx+1));
            } else {
                params.put(bit, "true");
            }
        }
        return params;
    }

    protected static native String getSearchString () /*-{
        return $wnd.location.search;
    }-*/;
}
