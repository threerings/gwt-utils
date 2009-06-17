//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

public class Anchor extends Widget
{
    /**
     * Create a new &lt;a tag, with properly escaped contents.
     *
     * @param href the href attribute for the tag.
     * @param text the inner text of this tag.  It will have all of its HTML entities escaped
     * automatically.
     * @param target the frame target of the anchor.
     * @param forceProtocol If true, http:// will be prepended if not already existing.
     * This also has the effect of killing malicious "javascript:" links. It's a good idea to
     * make this 'true' when displaying user specified URLs.
     */
    public Anchor (String href, String text, String target, boolean forceProtocol) 
    {
        _forceProtocol = forceProtocol;

        setElement(DOM.createAnchor());
        setHref(href);
        setText(text);
        if (target != null) {
            setFrameTarget(target);
        }
    }

    /**
     * Create a new &lt;a tag, with properly escaped contents.
     *
     * @param href the href attribute for the tag.
     * @param text the inner text of this tag.  It will have all of its HTML entities escaped
     * automatically.
     * @param target the frame target of the anchor.
     */
    public Anchor (String href, String text, String target)
    {
        this(href, text, target, false);
    }

    /**
     * Create a new &lt;a tag, with properly escaped contents.
     *
     * @param href the href attribute for the tag.
     * @param text the inner text of this tag.  It will have all of its HTML entities escaped
     * automatically.
     */
    public Anchor (String href, String text) 
    {
        this(href, text, null);
    }

    /**
     * Create an empty &lt;a tag.
     */
    public Anchor ()
    {
        setElement(DOM.createAnchor());
    }

    /**
     * Set the href location.
     */
    public void setHref (String href) 
    {
        if (_forceProtocol) {
            // This will correct URLs that don't have http:// as well
            // as provide protection against JS injection
            if ( ! href.matches("^\\s*\\w+://.*")) {
                href = "http://" + href;
            }
        } else {
            // Always do some sort of JS protection
            if (href.trim().toLowerCase().startsWith("javascript:")) {
                // He's been a naughty boy
                href = "#";
            }
        }

        DOM.setElementProperty(getElement(), "href", href);
    }

    /**
     * Set the inner text.  The text will have all of its HTML entities escaped automatically.
     */
    public void setText (String text)
    {
        // by using DOM methods, there is magically no need to escape most things.  
        DOM.setInnerText(getElement(), text);
    }

    /**
     * Sets this anchor's text as raw HTML. No escaping is done, so be careful.
     */
    public void setHTML (String html)
    {
        DOM.setInnerHTML(getElement(), html);
    }

    /**
     * Sets the frame target for this anchor.
     */
    public void setFrameTarget (String target)
    {
        DOM.setElementProperty(getElement(), "target", target);
    }

    protected boolean _forceProtocol;
}
