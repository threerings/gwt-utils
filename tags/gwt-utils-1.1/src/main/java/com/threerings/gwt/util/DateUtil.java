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

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Time and date utility methods.
 */
public class DateUtil
{
    /**
     * Creates a label of the form "9:15am". TODO: support 24 hour time for people who go for that
     * sort of thing. If date is null the empty string is returned.
     */
    public static String formatTime (Date date)
    {
        return (date == null) ? "" : _tfmt.format(date).toLowerCase();
    }

    /**
     * Formats the supplied date relative to the current time: Today, Yesterday, MMM dd, and
     * finally MMM dd, YYYY. If date is null the empty string is returned.
     */
    public static String formatDate (Date date)
    {
        return formatDate(date, true);
    }

    /**
     * Formats the supplied date relative to the current time: Today, Yesterday, MMM dd, and
     * finally MMM dd, YYYY. If date is null the empty string is returned.
     *
     * @param useShorthand if false, "Today" and "Yesterday" will not be used, only the month/day
     * and month/day/year formats.
     */
    public static String formatDate (Date date, boolean useShorthand)
    {
        if (date == null) {
            return "";
        }

        Date now = new Date();
        if (getYear(date) != getYear(now)) {
            return _yfmt.format(date);

        } else if (getMonth(date) != getMonth(now)) {
            return _mfmt.format(date);

        } else if (useShorthand && getDayOfMonth(date) == getDayOfMonth(now)) {
            return _msgs.today();

        // these will break for one hour on daylight savings time and we'll instead report the date
        // in MMM dd format or we'll call two days ago yesterday for that witching hour; we don't
        // have excellent date services in the browser, so we're just going to be OK with that
        } else if (useShorthand && getDayOfMonth(date) ==
                   getDayOfMonth(new Date(now.getTime()-24*60*60*1000))) {
            return _msgs.yesterday();

        } else if (useShorthand && getDayOfMonth(date) ==
                   getDayOfMonth(new Date(now.getTime()+ONE_DAY))) {
            return _msgs.tomorrow();

        } else {
            return _mfmt.format(date);
        }
    }

    /**
     * Creates a label of the form "{@link #formatDate} at {@link #formatTime}". If date is null
     * the empty string is returned.
     */
    public static String formatDateTime (Date date)
    {
        return (date == null) ? "" : _msgs.dateTime(formatDate(date), formatTime(date));
    }

    @SuppressWarnings("deprecation")
    public static Date toDate (int[] datevec)
    {
        return new Date(datevec[0] - 1900, datevec[1], datevec[2]);
    }

    @SuppressWarnings("deprecation")
    public static int[] toDateVec (Date date)
    {
        return new int[]{date.getYear() + 1900, date.getMonth(), date.getDate()};
    }

    @SuppressWarnings("deprecation")
    public static Date newDate (String dateStr)
    {
        return new Date(dateStr);
    }

    @SuppressWarnings("deprecation")
    public static int getDayOfMonth (Date date)
    {
        return date.getDate();
    }

    @SuppressWarnings("deprecation")
    public static int getMonth (Date date)
    {
        return date.getMonth();
    }

    /**
     * Returns the year component of the supplied date. <em>Note:</em> this is <em>not</em> the
     * year minus 1900 which the underlying {@link Date#getYear} method returns. It's the actual
     * year. Returning years since 1900 is stupid.
     */
    @SuppressWarnings("deprecation")
    public static int getYear (Date date)
    {
        return date.getYear() + 1900;
    }

    @SuppressWarnings("deprecation")
    public static void zeroTime (Date date)
    {
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }

    protected static final DateTimeFormat _tfmt = DateTimeFormat.getFormat("h:mmaa");
    protected static final DateTimeFormat _mfmt = DateTimeFormat.getFormat("MMM dd");
    protected static final DateTimeFormat _yfmt = DateTimeFormat.getFormat("MMM dd, yyyy");

    protected static final UtilMessages _msgs = GWT.create(UtilMessages.class);

    protected static final long ONE_DAY = 24*60*60*1000;
}
