//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Performs a wipe effect on a {@link SimplePanel} that contains another widget.
 */
public abstract class WipeAnimation extends Animation
{
    /**
     * Configures this animation to anchor the top of the revealed widget in place as the bottom is
     * revealed or hidden.
     */
    public WipeAnimation fromTop ()
    {
        _anchorBottom = false;
        return this;
    }

    /**
     * Configure this animation to anchor the bottom of the widget in place as it is revealed or
     * hidden. This causes the widget to appear as if it is sliding.
     */
    public WipeAnimation fromBottom ()
    {
        _anchorBottom = true;
        return this;
    }

    /**
     * Configures a command to be invoked when this animation is complete.
     */
    public WipeAnimation onComplete (Command onComplete)
    {
        _onComplete = onComplete;
        return this;
    }

    protected WipeAnimation (SimplePanel target)
    {
        _target = target;;
    }

    protected abstract int computeCurHeight (int targetHeight, double progress);

    @Override // from Animation
    protected void onStart ()
    {
        _targetHeight = _target.getWidget().getOffsetHeight();
        DOM.setStyleAttribute(_target.getElement(), "overflow", "hidden");
        if (_anchorBottom) {
            DOM.setStyleAttribute(_target.getWidget().getElement(), "position", "relative");
        }
        super.onStart();
    }

    @Override // from Animation
    protected void onUpdate (double progress)
    {
        int curHeight = computeCurHeight(_targetHeight, progress);
        DOM.setStyleAttribute(_target.getElement(), "height", curHeight + "px");
        if (_anchorBottom) {
            DOM.setStyleAttribute(_target.getWidget().getElement(),
                                  "top", (curHeight - _targetHeight) + "px");
        }
    }

    @Override // from Animation
    protected void onComplete ()
    {
        super.onComplete();
        DOM.setStyleAttribute(_target.getElement(), "overflow", "auto");
        DOM.setStyleAttribute(_target.getElement(), "height", "auto");
        if (_anchorBottom) {
            DOM.setStyleAttribute(_target.getWidget().getElement(), "position", "static");
        }
        if (_onComplete != null) {
            _onComplete.execute();
        }
    }

    protected SimplePanel _target;
    protected boolean _anchorBottom;
    protected Command _onComplete;
    protected int _targetHeight;
}
