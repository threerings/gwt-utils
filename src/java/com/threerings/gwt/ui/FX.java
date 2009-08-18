//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.SimplePanel;

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
}
