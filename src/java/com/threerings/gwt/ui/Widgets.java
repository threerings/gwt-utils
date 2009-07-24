//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
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
    public static SimplePanel newSimplePanel (String styleName, Widget widget)
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
     * Creates a FlowPanel with the supplied widgets.
     */
    public static FlowPanel newFlowPanel (Widget... contents)
    {
        return newFlowPanel(null, contents);
    }

    /**
     * Creates a FlowPanel with the provided style and widgets.
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
     * Wraps the supplied contents in a scroll panel that will set the max-width to
     * Window.getClientWidth()-xpad and the max-height to Window.getClientHeight()-ypad. If either
     * xpad or ypad are less than zero, the max-size attribute on that axis will not be set.
     */
    public static ScrollPanel newScrollPanel (Widget contents, int xpad, int ypad)
    {
        ScrollPanel panel = new ScrollPanel(contents);
        if (xpad >= 0) {
            String maxWidth = (Window.getClientWidth() - xpad) + "px";
            DOM.setStyleAttribute(panel.getElement(), "maxWidth", maxWidth);
        }
        if (ypad >= 0) {
            String maxHeight = (Window.getClientHeight() - ypad) + "px";
            DOM.setStyleAttribute(panel.getElement(), "maxHeight", maxHeight);
        }
        return panel;
    }

    /**
     * Wraps the supplied contents in a scroll panel with the specified maximum width.
     */
    public static ScrollPanel newScrollPanelX (Widget contents, int maxWidth)
    {
        ScrollPanel panel = new ScrollPanel(contents);
        DOM.setStyleAttribute(panel.getElement(), "maxWidth", maxWidth + "px");
        return panel;
    }

    /**
     * Wraps the supplied contents in a scroll panel with the specified maximum height.
     */
    public static ScrollPanel newScrollPanelY (Widget contents, int maxHeight)
    {
        ScrollPanel panel = new ScrollPanel(contents);
        DOM.setStyleAttribute(panel.getElement(), "maxHeight", maxHeight + "px");
        return panel;
    }

    /**
     * Creates a row of widgets in a horizontal panel with a 5 pixel gap between them.
     */
    public static HorizontalPanel newRow (Widget... contents)
    {
        return newRow(null, contents);
    }

    /**
     * Creates a row of widgets in a horizontal panel with a 5 pixel gap between them.
     */
    public static HorizontalPanel newRow (String styleName, Widget... contents)
    {
        return newRow(HasAlignment.ALIGN_MIDDLE, styleName, contents);
    }

    /**
     * Creates a row of widgets in a horizontal panel with a 5 pixel gap between them. The supplied
     * style name is added to the container panel.
     */
    public static HorizontalPanel newRow (HasAlignment.VerticalAlignmentConstant valign,
                                          String styleName, Widget... contents)
    {
        HorizontalPanel row = new HorizontalPanel();
        row.setVerticalAlignment(valign);
        if (styleName != null) {
            row.setStyleName(styleName);
        }
        for (Widget widget : contents) {
            if (row.getWidgetCount() > 0) {
                row.add(newShim(5, 5));
            }
            row.add(widget);
        }
        return row;
    }

    /**
     * Creates a label with the supplied text and style and optional additional styles.
     */
    public static Label newLabel (String text, String... styles)
    {
        Label label = new Label(text);
        for (String style : styles) {
            label.addStyleName(style);
        }
        return label;
    }

    /**
     * Creates an inline label with optional additional styles.
     */
    public static Label newInlineLabel (String text, String... auxStyles)
    {
        Label label = newLabel(text, auxStyles);
        label.addStyleName("inline");
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
        return makeActionLabel(newLabel(text, style), onClick);
    }

    /**
     * Makes the supplied label into an action label. The label will be styled such that it
     * configures the mouse pointer and adds underline to the text.
     */
    public static Label makeActionLabel (Label label, ClickHandler onClick)
    {
        if (onClick != null) {
            label.addStyleName("actionLabel");
            label.addClickHandler(onClick);
        }
        return label;
    }

    /**
     * Creates a new HTML element with the specified contents and style.
     */
    public static HTML newHTML (String text, String styleName, String... auxStyles)
    {
        HTML html = new HTML(text);
        if (styleName != null) {
            html.setStyleName(styleName);
        }
        for (String auxStyle : auxStyles) {
            html.addStyleName(auxStyle);
        }
        return html;
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
     *
     * @param width the width of the text area or -1 to use the default (or CSS styled) width.
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
     * Creates a limited text area with all of the configuration that you're bound to want to do.
     *
     * @param width the width of the text area or -1 to use the default (or CSS styled) width.
     */
    public static LimitedTextArea newTextArea (String text, int width, int height, int maxLength)
    {
        LimitedTextArea area = new LimitedTextArea(maxLength, width, height);
        if (text != null) {
            area.setText(text);
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

    /**
     * Makes a widget that takes up horizontal and or vertical space. Shim shimminy shim shim
     * shiree.
     */
    public static Widget newShim (int width, int height)
    {
        Label shim = new Label("");
        shim.setWidth(width + "px");
        shim.setHeight(height + "px");
        return shim;
    }

    protected static void maybeAddClickHandler (HasClickHandlers target, ClickHandler onClick)
    {
        if (onClick != null) {
            target.addClickHandler(onClick);
        }
    }
}
