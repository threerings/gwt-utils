//
// $Id$

package com.threerings.gwt.ui.fx;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

/**
 * An animation that moves a target from one position to another.
 */
public abstract class MoveAnimation extends Animation
{
    /**
     * Configures the starting position of the movement. The default starting position is the
     * target's current location.
     */
    public MoveAnimation from (int left, int top)
    {
        _fromLeft = left;
        _fromTop = top;
        return this;
    }

    /**
     * Configures the destination position of the movement. The default destination position is the
     * target's current location.
     */
    public MoveAnimation to (int left, int top)
    {
        _toLeft = left;
        _toTop = top;
        return this;
    }

    /**
     * Configures a command to be invoked when this animation is complete.
     */
    public MoveAnimation onComplete (Command onComplete)
    {
        _onComplete = onComplete;
        return this;
    }

    protected MoveAnimation (Widget target)
    {
        _fromLeft = _toLeft = target.getAbsoluteLeft();
        _fromTop = _toTop = target.getAbsoluteTop();
    }

    /** Updates the position of our target widget. */
    protected abstract void updatePosition (int left, int top);

    @Override // from Animation
    protected void onUpdate (double progress)
    {
        int curLeft = _fromLeft + (int)(progress * (_toLeft - _fromLeft));
        int curTop = _fromTop + (int)(progress * (_toTop - _fromTop));
        updatePosition(curLeft, curTop);
    }

    @Override // from Animation
    protected void onComplete ()
    {
        super.onComplete();
        if (_onComplete != null) {
            _onComplete.execute();
        }
    }

    protected int _fromLeft, _fromTop;
    protected int _toLeft, _toTop;
    protected Command _onComplete;
}
