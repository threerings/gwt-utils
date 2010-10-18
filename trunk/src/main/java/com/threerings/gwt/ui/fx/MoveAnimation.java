//
// $Id$
//
// OOO GWT Utils - utilities for creating GWT applications
// Copyright (C) 2009-2010 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/ooo-gwt-utils/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

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
