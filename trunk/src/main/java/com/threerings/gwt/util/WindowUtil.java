//
// $Id$

package com.threerings.gwt.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

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
            if (eqidx >= 0) {
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

    /**
     * Scrolls the window to place the specified target widget at the top (or as close as possible
     * given the height of the browser).
     */
    public static void scrollTo (Widget target)
    {
        Window.scrollTo(Window.getScrollLeft(), target.getElement().getAbsoluteTop());
    }

    /**
     * Returns the vertical scroll position needed to place the specified target widget in view,
     * while trying to minimize scrolling.
     */
    public static int getScrollIntoView (Widget target)
    {
        int top = Window.getScrollTop(), height = Window.getClientHeight();
        int ttop = target.getElement().getAbsoluteTop();
        int theight = target.getElement().getClientHeight();
        // if the target widget is taller than the browser window, or is above the current scroll
        // position of the browser window, scroll the top of the widget to the top of the window
        if (theight > height || ttop < top) {
            return ttop;
        // otherwise scroll the bottom of the widget to the bottom of the window
        } else if (ttop + theight > top + height) {
            return ttop - (height - theight);
        } else {
            return top; // no scrolling needed
        }
    }

    /**
     * Scrolls the window to place the specified target widget in view, while trying to minimize
     * scrolling.
     */
    public static void scrollIntoView (Widget target)
    {
        Window.scrollTo(Window.getScrollLeft(), getScrollIntoView(target));
    }

    /**
     * Returns the vertical scroll position needed to center the target widget vertically in the
     * browser viewport. If the widget is taller than the viewport, its top is returned.
     */
    public static int getScrollToMiddle (Widget target)
    {
        int wheight = Window.getClientHeight(), theight = target.getElement().getClientHeight();
        int ttop = target.getElement().getAbsoluteTop();
        return Math.max(ttop, ttop + (wheight - theight));
    }

    /**
     * Centers the target widget vertically in the browser viewport. If the widget is taller than
     * the viewport, its top is aligned with the top of the viewport.
     */
    public static void scrollToMiddle (Widget target)
    {
        Window.scrollTo(Window.getScrollLeft(), getScrollToMiddle(target));
    }
}
