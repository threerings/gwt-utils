//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import com.threerings.gwt.ui.fx.MoveAnimation;
import com.threerings.gwt.ui.fx.WipeAnimation;

/**
 * Creates animation effects.
 */
public class FX
{
    /**
     * Creates an animation that reveals the supplied target panel (wiping it from height zero to
     * full height). The animation can be subsequently configured via its fluent methods.
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
     * zero). The animation can be subsequently configured via its fluent methods.
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
     * position.
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
}
