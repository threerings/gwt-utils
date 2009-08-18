//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Reveals another widget via a sliding down animation.
 */
public class RevealPanel extends SimplePanel
{
    /**
     * Creates a panel that will reveal (and hide) the supplied target widget with a vertical wipe
     * animation.
     */
    public RevealPanel (Widget target)
    {
        this(DEFAULT_ANIM_TIME, target);
    }

    /**
     * Creates a panel that will reveal (and hide) the supplied target with a vertical wipe
     * animation that lasts the specified number of milliseconds.
     */
    public RevealPanel (long animTime, Widget target)
    {
        _animTime = animTime;
        add(target);
    }

    /**
     * Reveals the target widget with a vertical wipe. The panel will be height zero when the
     * animation starts and the full height of the target when it completes.
     */
    public void reveal ()
    {
        DOM.setStyleAttribute(getElement(), "overflow", "hidden");
        DOM.setStyleAttribute(getElement(), "height", "0px");
        _revealAnim.run(_animTime);
    }

    /**
     * Hides the target widget with a vertical wipe and then removes it from its parent panel when
     * the animation completes.
     */
    public void hideAndRemove ()
    {
        DOM.setStyleAttribute(getElement(), "overflow", "hidden");
        _targetHeight = getWidget().getOffsetHeight();
        _hideAnim.run(_animTime);
    }

    protected Animation _revealAnim = new Animation() {
        @Override protected void onUpdate (double progress) {
            // the target may not be fully laid out when we start our reveal so we update our
            // target height every frame so that we eventually know how big we want to be; this can
            // result in a little jitter at the start of the animation but usually isn't bad
            _targetHeight = getWidget().getOffsetHeight();
            int curHeight = (int) (progress * _targetHeight);
            DOM.setStyleAttribute(RevealPanel.this.getElement(), "height", curHeight + "px");
        }

        @Override protected void onComplete () {
            super.onComplete();
            DOM.setStyleAttribute(RevealPanel.this.getElement(), "overflow", "auto");
            DOM.setStyleAttribute(RevealPanel.this.getElement(), "height", "auto");
        }
    };

    protected Animation _hideAnim = new Animation() {
        @Override protected void onUpdate (double progress) {
            int curHeight = (int) ((1-progress) * _targetHeight);
            DOM.setStyleAttribute(RevealPanel.this.getElement(), "height", curHeight + "px");
        }

        @Override protected void onComplete () {
            super.onComplete();
            ((Panel)RevealPanel.this.getParent()).remove(RevealPanel.this);
        }
    };

    protected long _animTime;
    protected int _targetHeight;
}
