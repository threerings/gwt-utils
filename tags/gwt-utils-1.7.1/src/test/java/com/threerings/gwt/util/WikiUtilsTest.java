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

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests the {@link WikiUtils} class.
 */
public class WikiUtilsTest
{
    public static final String[] VALID_URIS = {
        "http://www.google.com/",
        "http://hh-1hallo.msn.blabla.com:80800/test/test/test.aspx?dd=dd&id=dki",
        "http://www.google.com/search?q=good+url+regex&" +
        "rls=com.microsoft:*&ie=UTF-8&oe=UTF-8&startIndex=&startPage=1",
        "ftp://joe:password@ftp.filetransferprotocol.com",
        "https://some-url.com?query=&name=joe?filter=*.*#some_anchor",
    };

    public static final String[] INVALID_URIS = {
        "http://www.google.com/ monkey",
        "your mom",
        "bob",
    };

    @Test public void testIsURI ()
    {
        for (String valid : VALID_URIS) {
            assertTrue(valid + " is valid.", WikiUtils.isURI(valid));
        }
        for (String invalid : INVALID_URIS) {
            assertFalse(invalid + " is invalid.", WikiUtils.isURI(invalid));
        }
    }
}
