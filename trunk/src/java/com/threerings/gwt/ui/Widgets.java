//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Convenience methods for creating and configuring widgets.
 */
public class Widgets
{
    /**
     * Creates a SimplePanel with the supplied style and widget
     */
    public static SimplePanel newSimplePanel (Widget widget, String styleName)
    {
        SimplePanel panel = new SimplePanel();
        if (styleName != null) {
            panel.addStyleName(styleName);
        }
        if (widget != null) {
            panel.setWidget(widget);
        }
        return panel;
    }

    /**
     * Creates a FlowPanel with the provided style
     */
    public static FlowPanel newFlowPanel (String styleName, Widget... contents)
    {
        FlowPanel panel = new FlowPanel();
        if (styleName != null) {
            panel.addStyleName(styleName);
        }
        for (Widget child : contents) {
            panel.add(child);
        }
        return panel;
    }

    /**
     * Creates a AbsolutePanel with the supplied style
     */
    public static AbsolutePanel newAbsolutePanel (String styleName)
    {
        AbsolutePanel panel = new AbsolutePanel();
        if (styleName != null) {
            panel.addStyleName(styleName);
        }
        return panel;
    }

    /**
     * Creates a label with the supplied text and style.
     */
    public static Label newLabel (String text, String styleName)
    {
        Label label = new Label(text);
        if (styleName != null) {
            label.setStyleName(styleName);
        }
        return label;
    }

    /**
     * Creates a label that triggers an action using the supplied text and handler.
     */
    public static Label newActionLabel (String text, ClickHandler onClick)
    {
        return newActionLabel(text, null, onClick);
    }

    /**
     * Creates a label that triggers an action using the supplied text and handler. The label will
     * be styled as specified with an additional style that configures the mouse pointer and adds
     * underline to the text.
     */
    public static Label newActionLabel (String text, String style, ClickHandler onClick)
    {
        Label label = newCustomActionLabel(text, style, onClick);
        if (onClick != null) {
            label.addStyleName("actionLabel");
        }
        return label;
    }

    /**
     * Creates a label that triggers an action using the supplied text and listener. The label will
     * only be styled with the specified style.
     */
    public static Label newCustomActionLabel (String text, String style, ClickHandler handler)
    {
        Label label = newLabel(text, style);
        maybeAddClickHandler(label, handler);
        return label;
    }

    /**
     * Creates an image with the supplied path and style.
     */
    public static Image newImage (String path, String styleName)
    {
        Image image = new Image(path);
        if (styleName != null) {
            image.addStyleName(styleName);
        }
        return image;
    }

    /**
     * Creates an image that responds to clicking.
     */
    public static Image newActionImage (String path, ClickHandler onClick)
    {
        return newActionImage(path, null, onClick);
    }

    /**
     * Creates an image that responds to clicking.
     */
    public static Image newActionImage (String path, String tip, ClickHandler onClick)
    {
        return makeActionImage(new Image(path), tip, onClick);
    }

    /**
     * Makes an image into one that responds to clicking.
     */
    public static Image makeActionImage (Image image, String tip, ClickHandler onClick)
    {
        if (onClick != null) {
            image.addStyleName("actionLabel");
            image.addClickHandler(onClick);
        }
        if (tip != null) {
            image.setTitle(tip);
        }
        return image;
    }

    /**
     * Creates an image that will render inline with text (rather than forcing a break).
     */
    public static Image newInlineImage (String path)
    {
        Image image = new Image(path);
        image.setStyleName("inline");
        return image;
    }

    /**
     * Creates a text box with all of the configuration that you're bound to want to do.
     */
    public static TextBox newTextBox (String text, int maxLength, int visibleLength)
    {
        return initTextBox(new TextBox(), text, maxLength, visibleLength);
    }

    /**
     * Configures a text box with all of the configuration that you're bound to want to do. This is
     * useful for configuring a PasswordTextBox.
     */
    public static TextBox initTextBox (TextBox box, String text, int maxLength, int visibleLength)
    {
        if (text != null) {
            box.setText(text);
        }
        box.setMaxLength(maxLength > 0 ? maxLength : 255);
        if (visibleLength > 0) {
            box.setVisibleLength(visibleLength);
        }
        return box;
    }

    /**
     * Creates a text area with all of the configuration that you're bound to want to do.
     */
    public static TextArea newTextArea (String text, int width, int height)
    {
        TextArea area = new TextArea();
        if (text != null) {
            area.setText(text);
        }
        if (width > 0) {
            area.setCharacterWidth(width);
        }
        if (height > 0) {
            area.setVisibleLines(height);
        }
        return area;
    }

    /**
     * Creates a PushButton with default(up), mouseover and mousedown states.
     */
    public static PushButton newPushButton (Image defaultImage, Image overImage,
        Image downImage, ClickHandler onClick)
    {
        PushButton button = new PushButton(defaultImage, downImage, onClick);
        button.getUpHoveringFace().setImage(overImage);
        return button;
    }

    /**
     * Creates an image button that changes appearance when you click and hover over it.
     */
    public static PushButton newImageButton (String style, ClickHandler onClick)
    {
        PushButton button = new PushButton();
        button.setStyleName(style);
        button.addStyleName("actionLabel");
        maybeAddClickHandler(button, onClick);
        return button;
    }

    protected static void maybeAddClickHandler (HasClickHandlers target, ClickHandler onClick)
    {
        if (onClick != null) {
            target.addClickHandler(onClick);
        }
    }
}
