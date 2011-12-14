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

// import com.google.common.net.InternetDomainName;
import com.google.gwt.user.client.Window.Location;

/**
 * Wraps the necessary JavaScript fiddling to read and write cookies on the
 * client.
 */
public class CookieUtil
{
    /**
     * Sets the specified cookie to the supplied value.
     *
     * @param expires The number of days in which the cookie should expire.
     * @param domain The domain to set this cookie on.
     */
    public static void set (
        String path, int expires, String name, String value, String domain)
    {
        String extra = "";
        if (path.length() > 0) {
            extra += "; path=" + path;
        }
        if (domain != null) {
            extra += "; domain=" + domain;
        }
        doSet(name, value, expires, extra);
    }

    /**
     * Sets a cross-domain cookie to the supplied value, which can be accessed by other subdomains.
     *
     * WARNING!
     * This doesn't work if you use it on a top level domain. InternetDomainName is the correct way
     * to get the parent domain, but GWT fails to compile it (bug?). For backwards compatibility,
     * the existing behavior of manually guessing the parent domain remains the default. If you try
     * to use this on a top level owned domain (foo.com) this will try to set a cookie on an invalid
     * domain (.com) which no browser will allow.
     *
     * Instead, call set() with an explicit domain, or null if you don't care about cross-domain
     * cookies.
     */
    public static void set (String path, int expires, String name, String value)
    {
        // String domain = "." + InternetDomainName.from(
        //     Location.getHostName()).topPrivateDomain().name();

        String domain = Location.getHostName();
        int didx = domain.indexOf(".");
        if (didx != -1) {
            domain = domain.substring(didx);
        }

        set(path, expires, name, value, domain);
    }

    /**
     * Clears out the specified cookie.
     */
    public static void clear (String path, String name)
    {
        set(path, -1, name, "");
    }

    /**
     * Looks up and returns the value for the specified cookie.
     */
    public static native String get (String name) /*-{
        var dc = $doc.cookie;
        var prefix = name + "=";
        var begin = dc.indexOf("; " + prefix);
        if (begin == -1) {
            begin = dc.indexOf(prefix);
            if (begin != 0) {
                return null;
            }
        } else {
            begin += 2;
        }
        var end = $doc.cookie.indexOf(";", begin);
        if (end == -1) {
            end = dc.length;
        }
        return unescape(dc.substring(begin + prefix.length, end));
     }-*/;

    /**
     * Handles the actual setting of the cookie.
     */
    protected static native void doSet (String name, String value, int expires, String extra) /*-{
        if (expires != 0) {
            var date = new Date();
            date.setTime(date.getTime() + (expires*24*60*60*1000));
            extra += "; expires=" + date.toGMTString();
        }
        $doc.cookie = name + "=" + escape(value) + extra;
    }-*/;
}
