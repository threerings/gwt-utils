//
// $Id$

package com.threerings.gwt.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Maintains a stack of {@link PopupPanel}s. When a new panel is pushed onto the stack, the
 * currently visible panel is hidden and the new panel is shown. When the new panel is closed, the
 * previous panel on the stack is restored to view.
 */
public class PopupStack
{
    /**
     * Pushes any currently showing popup onto the stack and displays the supplied popup. The popup
     * will be made visible via a call to {@link PopupPanel#center}.
     */
    public void show (PopupPanel popup)
    {
        if (_showingPopup != null) {
            // clear out _showingPopup before hiding this popup to avoid triggering the close
            // handler logic
            PopupPanel toClose = _showingPopup;
            _popups.add(_showingPopup);
            _showingPopup = null;
            toClose.hide();
        }
        popup.addCloseHandler(new CloseHandler<PopupPanel>() {
            public void onClose (CloseEvent<PopupPanel> event) {
                if (_showingPopup == event.getTarget()) {
                    if (_popups.size() > 0) {
                        _showingPopup = _popups.remove(_popups.size()-1);
                        _showingPopup.center();
                    } else {
                        _showingPopup = null;
                    }
                }
            }
        });
        _showingPopup = popup;
        _showingPopup.center();
    }

    /**
     * Clears out the stack completely, removing all pending popups and clearing any currently
     * showing popup.
     */
    public void clear ()
    {
        _popups.clear();
        if (_showingPopup != null) {
            _showingPopup.hide();
            _showingPopup = null;
        }
    }

    protected PopupPanel _showingPopup;
    protected List<PopupPanel> _popups = new ArrayList<PopupPanel>();
}
