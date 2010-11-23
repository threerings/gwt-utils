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
 * Tests some additions made to {@link WikiParser}.
 */
public class WikiParserTest
{
    @Test public void testBackTick ()
    {
        assertEquals("<p>This is <code>some code</code>.</p>\n",
                     WikiParser.render("This is `some code`."));
        assertEquals("<p>This is <code>some == code</code>.</p>\n",
                     WikiParser.render("This is `some == code`."));
        assertEquals("<p>This is <code>some ** code **</code>.</p>\n",
                     WikiParser.render("This is `some ** code **`."));
    }

    @Test public void testStrikeThrough ()
    {
        assertEquals("<p>This is <strike>struck out</strike>.</p>\n",
                     WikiParser.render("This is --struck out--."));
        assertEquals("<strike>nothing but struck out</strike>",
                     WikiParser.renderSnippet("--nothing but struck out--"));
        assertEquals("--oops, forgot the closing delimiter",
                     WikiParser.renderSnippet("--oops, forgot the closing delimiter"));
        // make sure we didn't break <hr>s
        assertEquals("\n<hr/>\n", WikiParser.render("----"));
    }

    @Test public void testRenderSnippet ()
    {
        assertEquals("This is <code>some code</code>.",
                     WikiParser.renderSnippet("This is `some code`."));
        assertEquals("This is <strong>bold text</strong>.",
                     WikiParser.renderSnippet("This is **bold text**."));
        assertEquals("This is <em>italic text</em>. And some <code>monkeys</code>.",
                     WikiParser.renderSnippet("This is //italic text//. And some `monkeys`."));
    }

    @Test public void testFloat ()
    {
        assertEquals("<div style=\"float: left; margin-right: 5px\">left floating text</div>",
                     WikiParser.render("<<< left floating text"));
        assertEquals("<div style=\"float: right; margin-left: 5px\">right floating text</div>",
                     WikiParser.render(">>> right floating text"));
    }

    @Test public void testEmdash ()
    {
        // make sure we don't erroneously identify an mdash as a strikethrough
        assertEquals("This is some code with an &mdash; in it.",
                     WikiParser.renderSnippet("This is some code with an -- in it."));
        assertEquals("<p>This is some code with an &mdash; in it.</p>\n",
                     WikiParser.render("This is some code with an -- in it."));
    }
}
