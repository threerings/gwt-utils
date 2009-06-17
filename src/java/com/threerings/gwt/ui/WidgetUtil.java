//
// $Id$

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
     * Creates the HTML to display a transparent Flash movie for the browser on which we're running.
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static HTML createTransparentFlashContainer (
        String ident, String movie, int width, int height, String flashVars)
    {
        return createTransparentFlashContainer(ident, movie, ""+width, ""+height, flashVars);
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
        return _impl.createFlashContainer(ident, movie, width, height, flashVars, true);
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
        return createFlashContainer(ident, movie, ""+width, ""+height, flashVars);
    }

    /**
     * Creates the HTML to display a Flash movie for the browser on which we're running.
     *
     * @param flashVars a pre-URLEncoded string containing flash variables, or null.
     *        http://www.adobe.com/cfusion/knowledgebase/index.cfm?id=tn_16417
     */
    public static HTML createFlashContainer (String ident, String movie, String width,
                                             String height, String flashVars)
    {
        return _impl.createFlashContainer(ident, movie, width, height, flashVars, false);
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
        return createFlashObjectDefinition(ident, movie, ""+width, ""+height, flashVars);
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
        return _impl.createFlashObjectDefinition(ident, movie, width, height, flashVars, false);
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
     * Creates a <param> tag with the supplied name and value.
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

    protected static WidgetUtilImpl _impl = (WidgetUtilImpl)GWT.create(WidgetUtilImpl.class);
}
