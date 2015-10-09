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
     * that have no value will be mapped to "true". For example: {@code ?foo=bar&biff=baz&boink}
     * will be returned in a map with {@code { foo -> bar, biff -> baz, boink -> true }}.
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
        Window.scrollTo(Window.getScrollLeft(), target.getAbsoluteTop());
    }

    /**
     * Returns the vertical scroll position needed to place the specified target widget in view,
     * while trying to minimize scrolling.
     */
    public static int getScrollIntoView (Widget target)
    {
        int top = Window.getScrollTop(), height = Window.getClientHeight();
        int ttop = target.getAbsoluteTop(), theight = target.getOffsetHeight();
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
        int wheight = Window.getClientHeight(), theight = target.getOffsetHeight();
        int ttop = target.getAbsoluteTop();
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

    /**
     * Returns true if the target widget is at least partially scrolled into view. At least 10
     * pixels of the widget must be visible.
     */
    public static boolean isScrolledIntoView (Widget target)
    {
        return isScrolledIntoView(target, 10);
    }

    /**
     * Returns true if the target widget is vertically scrolled into view.
     *
     * @param minPixels the minimum number of pixels that must be visible to count as "in view".
     */
    public static boolean isScrolledIntoView (Widget target, int minPixels)
    {
        int wtop = Window.getScrollTop(), wheight = Window.getClientHeight();
        int ttop = target.getAbsoluteTop();
        if (ttop > wtop) {
            return (wtop + wheight - ttop > minPixels);
        } else {
            return (ttop + target.getOffsetHeight() - wtop > minPixels);
        }
    }

    /**
     * Returns true if the target widget is fully vertically visible. This will always return false
     * if the widget is taller than the window viewport, so don't call this in a feedback loop in a
     * misdirected attempt to scroll the widget into view.
     */
    public static boolean isFullyScrolledIntoView (Widget target)
    {
        return isScrolledIntoView(target, target.getOffsetHeight());
    }
}
