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

package com.threerings.gwt.ui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import com.threerings.gwt.ui.fx.MoveAnimation;
import com.threerings.gwt.ui.fx.WipeAnimation;

/**
 * Creates animation effects.
 */
public class FX
{
    /**
     * Creates an animation that reveals the supplied target panel (wiping it from height zero to
     * full height). The animation can be subsequently configured via its fluent methods. The
     * returned animation is configured fluently, for example:
     *
     * <code>FX.reveal(target).fromTop().onComplete(action).run(250);</code>
     */
    public static WipeAnimation reveal (final SimplePanel target)
    {
        return new WipeAnimation(target) {
            @Override protected void onStart () {
                DOM.setStyleAttribute(target.getElement(), "height", "0px");
                super.onStart();
            }
            @Override protected void onUpdate (double progress) {
                // the target may not be fully laid out when we start so we update our target
                // height every frame so that we eventually know how big we want to be; this can
                // result in a little jitter at the start of the animation but usually isn't bad
                _targetHeight = target.getWidget().getOffsetHeight();
                super.onUpdate(progress);
            }
            @Override protected int computeCurHeight (int targetHeight, double progress) {
                return (int) (progress * targetHeight);
            }
        };
    }

    /**
     * Creates an animation that unreveals the supplied target panel (wiping it down to height
     * zero). The animation can be subsequently configured via its fluent methods. The returned
     * animation is configured fluently, for example:
     *
     * <code>FX.unreveal(target).fromBottom().onComplete(action).run(250);</code>
     */
    public static WipeAnimation unreveal (SimplePanel target)
    {
        return new WipeAnimation(target) {
            @Override protected int computeCurHeight (int targetHeight, double progress) {
                return (int) ((1-progress) * targetHeight);
            }
        };
    }

    /**
     * Creates an animation that will move the specified popup panel. If the popup is not showing
     * or not visible, it will be shown and made visible immediately after moving it to the start
     * position. The returned animation is configured fluently, for example:
     *
     * <code>FX.move(target).from(x, y).to(dx, dy).onComplete(action).run(500);</code>
     */
    public static MoveAnimation move (final PopupPanel target)
    {
        return new MoveAnimation(target) {
            @Override protected void updatePosition (int left, int top) {
                target.setPopupPosition(left, top);
            }
            @Override protected void onStart () {
                super.onStart();
                if (!target.isShowing()) {
                    target.show();
                }
                target.setVisible(true);
            }
        };
    }

    /**
     * Creates an animation that will move the specified child of the specified absolute panel. The
     * returned animation is configured fluently, for example:
     *
     * <code>FX.move(panel, target).from(x, y).to(dx, dy).onComplete(action).run(500);</code>
     */
    public static MoveAnimation move (final AbsolutePanel parent, final Widget target)
    {
        return new MoveAnimation(target) {
            @Override protected void updatePosition (int left, int top) {
                parent.setWidgetPosition(target, left, top);
            }
        };
    }

    /**
     * Returns a command that, when executed itself, will execute the supplied command after the
     * specified millisecond delay. For convenience purposes, if a null command is supplied, null
     * is returned.
     */
    public static Command delay (final Command command, final int delay)
    {
        return (command == null) ? null : new Command() {
            public void execute () {
                new Timer() {
                    @Override public void run () {
                        command.execute();
                    }
                }.schedule(delay);
            }
        };
    }
}
