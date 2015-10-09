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

package com.threerings.gwt.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Contains some widget-related utility functions.
 */
public class WidgetUtil
{
    /** The minimum flash player version. */
    public static String FLASH_VERSION = "9,0,0,0";

    /**
     * Parameters for embedding a flash movie in an html page.
     */
    public static class FlashObject
    {
        /** The id and/or name of the embed tag. */
        public String ident;

        /** The url of the movie file to embed. */
        public String movie;

        /** The value of the "width" embed parameter for the movie. */
        public String width;

        /** The value of the "height" embed parameter for the movie. */
        public String height;

        /** The pre-URLEncoded flash variables to pass to the movie. Defaults to null. */
        public String flashVars;

        /** The value of the "bgcolor" embed parameter for the movie, normally a hash-prefixed hex
         * RGB tuple. Defaults to black. */
        public String bgcolor = "#000000";

        /** Whether the embedded movie should be marked as transparent. Defaults to false. */
        public boolean transparent;

        /**
         * Creates a new flash object definition with the given members using pixel css width and
         * height and other members set to default values.
         */
        public FlashObject (String ident, String movie, int width, int height)
        {
            this(ident, movie, width, height, null);
        }

        /**
         * Creates a new flash object definition with the given members using pixel css width and
         * height and other members set to default values.
         */
        public FlashObject (String ident, String movie, int width, int height, String flashVars)
        {
            this(ident, movie, ""+width, ""+height, flashVars);
        }

        /**
         * Creates a new flash object definition with the given members and other members default.
         */
        public FlashObject (String ident, String movie, String width, String height)
        {
            this(ident, movie, width, height, null);
        }

        /**
         * Creates a new flash object definition with the given members and other members default.
         */
        public FlashObject (String ident, String movie, String width, String height,
                            String flashVars)
        {
            this.ident = ident;
            this.movie = movie;
            this.width = width;
            this.height = height;
            this.flashVars = flashVars;
        }
    }

    /**
     * Creates the HTML to display a transparent Flash movie for the browser on which we're running.
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static HTML createTransparentFlashContainer (
        String ident, String movie, int width, int height, String flashVars)
    {
        FlashObject obj = new FlashObject(ident, movie, width, height, flashVars);
        obj.transparent = true;
        return createContainer(obj);
    }

    /**
     * Creates the HTML to display a transparent Flash movie for the browser on which we're running.
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static HTML createTransparentFlashContainer (String ident, String movie, String width,
                                             String height, String flashVars)
    {
        FlashObject obj = new FlashObject(ident, movie, width, height, flashVars);
        obj.transparent = true;
        return createContainer(obj);
    }

    /**
     * Creates the HTML to display a Flash movie for the browser on which we're running.
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static HTML createFlashContainer (
        String ident, String movie, int width, int height, String flashVars)
    {
        return createContainer(new FlashObject(ident, movie, width, height, flashVars));
    }

    /**
     * Creates the HTML to display a Flash movie for the browser on which we're running.
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static HTML createFlashContainer (
        String ident, String movie, String width, String height, String flashVars)
    {
        return createContainer(new FlashObject(ident, movie, width, height, flashVars));
    }

    /**
     * Creates the HTML to display a Flash movie for the browser on which we're running.
     */
    public static HTML createContainer (FlashObject obj)
    {
        return _impl.createContainer(obj);
    }

    /**
     * Creates an HTML string that represents the Flash object for the current browser.
     * This object can be added to the appropriate container using embedFlashObject().
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static String createFlashObjectDefinition (
        String ident, String movie, int width, int height, String flashVars)
    {
        return createDefinition(new FlashObject(ident, movie, width, height, flashVars));
    }

    /**
     * Creates an HTML string that represents the Flash object for the current browser.
     * This object can be added to the appropriate container using embedFlashObject().
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static String createFlashObjectDefinition (
        String ident, String movie, String width, String height, String flashVars)
    {
        return createDefinition(new FlashObject(ident, movie, width, height, flashVars));
    }

    public static String createDefinition (FlashObject obj)
    {
        return _impl.createDefinition(obj);
    }

    /**
     * Given an HTML string that defines a Flash object, creates a new DOM node for it and adds it
     * to the appropriate container. Please note: the container should be added to the page prior
     * to calling this function.
     */
    public static HTML embedFlashObject (Panel container, String htmlString)
    {
        // Please note: the following is a work-around for an IE7 bug. If we create a Flash object
        // node *before* attaching it to the DOM tree, IE will silently fail to register
        // the Flash object's callback functions for access from JavaScript. To make this work,
        // create an empty node first, add it to the DOM tree, and then initialize it with
        // the Flash object definition.
        HTML element = new HTML();
        container.add(element);
        element.setHTML(htmlString);
        return element;
    }

    /**
     * Creates the HTML to display a Java applet for the browser on which we're running.
     */
    public static HTML createApplet (String ident, String archive, String clazz, int width,
                                     int height, boolean mayScript, String[] params)
    {
        return createApplet(ident, archive, clazz, ""+width, ""+height, mayScript, params);
    }

    /**
     * Creates the HTML to display a Java applet for the browser on which we're running.
     */
    public static HTML createApplet (String ident, String archive, String clazz, String width,
                                     String height, boolean mayScript, String[] params)
    {
        String ptags = "";
        for (int ii = 0; ii < params.length; ii += 2) {
            ptags = ptags + "<param name=\"" + params[ii] + "\" " + "value=\"" + params[ii+1] +
                "\"/>";
        }
        return _impl.createApplet(ident, archive, clazz, width, height, mayScript, ptags);
    }

    /**
     * Creates a {@code <param>} tag with the supplied name and value.
     */
    public static Element createParam (String name, String value)
    {
        Element pelem = DOM.createElement("param");
        DOM.setElementProperty(pelem, "name", name);
        DOM.setElementProperty(pelem, "value", value);
        return pelem;
    }

    /**
     * Makes a widget that takes up horizontal and or vertical space. Shim shimminy shim shim
     * shiree.
     */
    public static Widget makeShim (int width, int height)
    {
        Label shim = new Label("");
        shim.setWidth(width + "px");
        shim.setHeight(height + "px");
        return shim;
    }

    /**
     * Chops off any non-numeric suffix.
     */
    public static String ensurePixels (String value)
    {
        int index = 0;
        for (int nn = value.length(); index < nn; index++) {
            char c = value.charAt(index);
            if (c < '0' || c > '9') {
                break;
            }
        }
        return value.substring(0, index);
    }

    protected static WidgetUtilImpl _impl = GWT.create(WidgetUtilImpl.class);
}
