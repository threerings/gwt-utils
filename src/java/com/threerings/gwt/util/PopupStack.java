//
// $Id$

package com.threerings.gwt.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.ui.Popups;

/**
 * Maintains a stack of {@link PopupPanel}s. When a new panel is pushed onto the stack, the
 * currently visible panel is hidden and the new panel is shown. When the new panel is closed, the
 * previous panel on the stack is restored to view.
 */
public class PopupStack
{
    /** A value indicating whether or not a popup is currently showing. */
    public final Value<Boolean> popupShowing;

    public PopupStack ()
    {
        this.popupShowing = _showingPopup.map(new Function<PopupPanel, Boolean>() {
            public Boolean apply (PopupPanel panel) {
                return (panel != null);
            }
        });
    }

    /**
     * Pushes any currently showing popup onto the stack and displays the supplied popup. The popup
     * will be made visible via a call to {@link PopupPanel#center}.
     */
    public void show (PopupPanel popup)
    {
        show(popup, null);
    }

    /**
     * Pushes any currently showing popup onto the stack and displays the supplied popup. The popup
     * will centered horizontally in the page and centered vertically around the supplied widget.
     */
    public void show (PopupPanel popup, Widget onCenter)
    {
        PopupPanel showing = _showingPopup.get();
        if (showing != null) {
            // null _showingPopup before hiding to avoid triggering the close handler logic
            _popups.add(showing);
            _showingPopup.update(null);
            showing.hide();
        }
        popup.addCloseHandler(new CloseHandler<PopupPanel>() {
            public void onClose (CloseEvent<PopupPanel> event) {
                if (_showingPopup.get() == event.getTarget()) {
                    if (_popups.size() > 0) {
                        _showingPopup.update(_popups.remove(_popups.size()-1));
                        _showingPopup.get().center();
                    } else {
                        _showingPopup.update(null);
                    }
                }
            }
        });
        _showingPopup.update(popup);

        if (onCenter == null) {
            popup.center(); // this will show the popup
        } else {
            Popups.centerOn(popup, onCenter);
        }
    }

    /**
     * Clears out the stack completely, removing all pending popups and clearing any currently
     * showing popup.
     */
    public void clear ()
    {
        _popups.clear();
        if (_showingPopup.get() != null) {
            _showingPopup.get().hide();
            _showingPopup.update(null);
        }
    }

    protected Value<PopupPanel> _showingPopup = Value.create(null);
    protected List<PopupPanel> _popups = new ArrayList<PopupPanel>();
}
