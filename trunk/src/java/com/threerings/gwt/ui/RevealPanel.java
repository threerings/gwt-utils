//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.Command;
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
    public RevealPanel (int animTime, Widget target)
    {
        _animTime = animTime;
        add(target);
    }

    /**
     * Reveals the target widget with a vertical wipe. The panel will be height zero when the
     * animation starts and the full height of the target when it completes.
     *
     * @param onComplete an optional command to execute when the animation is completd.
     */
    public void reveal (final Command onComplete)
    {
        DOM.setStyleAttribute(getElement(), "overflow", "hidden");
        DOM.setStyleAttribute(getElement(), "height", "0px");
        new Animation() {
            @Override protected void onUpdate (double progress) {
                // the target may not be fully laid out when we start so we update our target
                // height every frame so that we eventually know how big we want to be; this can
                // result in a little jitter at the start of the animation but usually isn't bad
                _targetHeight = getWidget().getOffsetHeight();
                int curHeight = (int) (progress * _targetHeight);
                DOM.setStyleAttribute(RevealPanel.this.getElement(), "height", curHeight + "px");
            }

            @Override protected void onComplete () {
                super.onComplete();
                DOM.setStyleAttribute(RevealPanel.this.getElement(), "overflow", "auto");
                DOM.setStyleAttribute(RevealPanel.this.getElement(), "height", "auto");
                if (onComplete != null) {
                    onComplete.execute();
                }
            }
        }.run(_animTime);
    }

    /**
     * Hides the target widget with a vertical wipe and then removes it from its parent panel when
     * the animation completes.
     *
     * @param onComplete an optional command to execute when the animation is completd.
     */
    public void hideAndRemove (final Command onComplete)
    {
        DOM.setStyleAttribute(getElement(), "overflow", "hidden");
        _targetHeight = getWidget().getOffsetHeight();

        new Animation() {
            @Override protected void onUpdate (double progress) {
                int curHeight = (int) ((1-progress) * _targetHeight);
                DOM.setStyleAttribute(RevealPanel.this.getElement(), "height", curHeight + "px");
            }

            @Override protected void onComplete () {
                super.onComplete();
                ((Panel)RevealPanel.this.getParent()).remove(RevealPanel.this);
                if (onComplete != null) {
                    onComplete.execute();
                }
            }
        }.run(_animTime);
    }

    protected int _animTime, _targetHeight;

    protected static final int DEFAULT_ANIM_TIME = 500;
}
