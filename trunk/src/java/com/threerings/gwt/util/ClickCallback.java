//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import com.threerings.gwt.ui.EnterClickAdapter;

/**
 * Allows one to wire up a button and a service call into one concisely specified little chunk of
 * code. Be sure to call <code>super.onSuccess()</code> and <code>super.onFailure()</code> if you
 * override those methods so that they can automatically reenable the trigger button.
 *
 * <p> When using the ClickCallback on a Label, the callback automatically adds the style
 * <code>actionLabel</code> to the label and removes the style when the label is disabled during
 * the service call. This style should at a minimum add 'text-decoration: underline; cursor: hand'
 * to the label to indicate to the user that it is clickable.
 */
public abstract class ClickCallback<T>
    implements AsyncCallback<T>
{
    /**
     * Creates a callback for the supplied trigger (the constructor will automatically add this
     * callback to the trigger as a click listener). Failure will automatically be reported.
     */
    public ClickCallback (HasClickHandlers trigger)
    {
        this(trigger, null);
    }

    /**
     * Creates a callback for the supplied trigger (the constructor will automatically add this
     * callback to the trigger as a click listener). Failure will automatically be reported.
     */
    public ClickCallback (HasClickHandlers trigger, TextBox onEnter)
    {
        _trigger = trigger;
        _onEnter = onEnter;
        setEnabled(true); // this will wire up all of our bits
    }

    // from interface AsyncCallback
    public void onSuccess (T result)
    {
        setEnabled(gotResult(result));
    }

    // from interface AsyncCallback
    public void onFailure (Throwable cause)
    {
        Console.log("Callback failure", "for", _trigger, cause);
        setEnabled(true);
        reportFailure(cause);
    }

    /**
     * This method is called when the trigger button is clicked. Pass <code>this</code> as the
     * {@link AsyncCallback} to a service method. Return true from this method if a service request
     * was initiated and the button that triggered it should be disabled.
     */
    protected abstract boolean callService ();

    /**
     * This method will be called when the service returns successfully. Return true if the trigger
     * should now be reenabled, false to leave it disabled.
     */
    protected abstract boolean gotResult (T result);

    /**
     * Reports a failure to the user. Derived classes can override this and customize the way
     * failure is reported if they so desire.
     */
    protected abstract void reportFailure (Throwable cause);

    protected void takeAction ()
    {
        if (callService()) {
            setEnabled(false);
        }
    }

    protected void setEnabled (boolean enabled)
    {
        if (_trigger instanceof FocusWidget) {
            ((FocusWidget)_trigger).setEnabled(enabled);

        } else if (_trigger instanceof Label) {
            Label tlabel = (Label)_trigger;
            tlabel.removeStyleName("actionLabel");
            if (enabled) {
                tlabel.addStyleName("actionLabel");
            }
        }

        // always remove first so that if we do end up adding, we don't doubly add
        if (_clickreg != null) {
            _clickreg.removeHandler();
            _clickreg = null;
        }
        if (_enterreg != null) {
            _enterreg.removeHandler();
            _enterreg = null;
        }
        if (enabled) {
            _clickreg = _trigger.addClickHandler(_onClick);
            if (_onEnter != null) {
                _onEnter.addKeyPressHandler(new EnterClickAdapter(_onClick));
            }
        }
    }

    protected ClickHandler _onClick = new ClickHandler() {
        public void onClick (ClickEvent event) {
            takeAction();
        }
    };

    protected HasClickHandlers _trigger;
    protected HandlerRegistration _clickreg;

    protected TextBox _onEnter;
    protected HandlerRegistration _enterreg;
}
