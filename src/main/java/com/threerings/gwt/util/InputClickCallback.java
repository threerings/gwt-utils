package com.threerings.gwt.util;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.ui.EnterClickAdapter;
import com.threerings.gwt.ui.SmartTable;

/**
 * Provides a click handler that will first request an input string from the user using a popup
 * and then perform a service call with the input string. The input widget is provided on
 * construction and the normally anonymous subclass supplies the service handling.
 *
 * <p>NOTE: the input popup replaces the confirmation popup.</p>
 */
public abstract class InputClickCallback<T, W extends Widget & HasText & HasKeyDownHandlers & HasChangeHandlers>
        extends ClickCallback<T>
{
    /**
     * Creates a new click handler for the given trigger. Upon clicking, text input will be
     * requested from the user by popping up a confirmation with the given widget.
     */
    public InputClickCallback (HasClickHandlers trigger, W textInputWidget)
    {
        super(trigger);
        setConfirmText("");
        _widget = textInputWidget;
    }

    /**
     * Sets the text to be shown above the input explaining what the user should do. Note that this
     * just calls {@link #setConfirmText(String)}, but is included here for clarity.
     */
    InputClickCallback<T, W> setPromptText(String prompt)
    {
        setConfirmText(prompt);
        return this;
    }

    /**
     * Sets the html to be shown above the input explaining what the user should do. Note that this
     * just calls {@link #setConfirmHTML(String)}, but is included here for clarity.
     */
    InputClickCallback<T, W> setPromptHTML(String promptHTML)
    {
        setConfirmHTML(promptHTML);
        return this;
    }

    @Override
    protected boolean callService ()
    {
        return callService(_widget.getText());
    }

    /**
     * Makes the asynchronous service call, returning true only if the call was actually made.
     * @see #callService()
     */
    abstract protected boolean callService (String input);

    @Override
    protected void onAborted ()
    {
        super.onAborted();
        removeHandlerReg();
    }

    @Override
    protected void onConfirmed ()
    {
        super.onConfirmed();
        removeHandlerReg();
    }

    /**
     * Removes the change handler registration that was added for the confirmation popup.
     */
    protected void removeHandlerReg ()
    {
        _changeHandlerReg.removeHandler();
        _changeHandlerReg = null;
    }

    @Override
    protected int addConfirmPopupMessage (SmartTable contents, int row)
    {
        row = super.addConfirmPopupMessage(contents, row);
        contents.setWidget(row, 0, _widget);
        contents.getFlexCellFormatter().setColSpan(row, 0, 2);
        row++;

        contents.setWidget(row, 0, _widget);
        contents.getFlexCellFormatter().setColSpan(row, 0, 2);
        contents.getFlexCellFormatter().setWidth(row, 0, "100%");
        row++;

        return row;
    }

    @Override
    protected ButtonBase createConfirmButton (String text, ClickHandler onClick)
    {
        final ButtonBase button = super.createConfirmButton(text, onClick);
        EnterClickAdapter.bind(_widget, onClick);
        _changeHandlerReg = _widget.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange (ChangeEvent event)
            {
                String text = _widget.getText();
                button.setEnabled(text != null && text.length() > 0);
            }
        });
        return button;
    }

    protected W _widget;
    protected HandlerRegistration _changeHandlerReg;
}
