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
    /** Anchor constants. */
    public enum Anchor { TOP, BOTTOM }

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
     * @param anchor if TOP the top of the widget will remain in place as the bottom is revealed,
     * if BOTTOM, the bottom of the widget will move along with the wipe as if the widget was being
     * slid down onto the screen.
     * @param onComplete an optional command to execute when the animation is completd.
     */
    public void reveal (Anchor anchor, Command onComplete)
    {
        new WipeAnimation(anchor, onComplete) {
            @Override protected void onStart () {
                DOM.setStyleAttribute(getElement(), "height", "0px");
                super.onStart();
            }
            @Override protected void onUpdate (double progress) {
                // the target may not be fully laid out when we start so we update our target
                // height every frame so that we eventually know how big we want to be; this can
                // result in a little jitter at the start of the animation but usually isn't bad
                _targetHeight = getWidget().getOffsetHeight();
                super.onUpdate(progress);
            }
            @Override protected int computeCurHeight (double progress) {
                return (int) (progress * _targetHeight);
            }
        }.run(_animTime);
    }

    /** Convienence form of {@link #reveal}. */
    public void revealFromTop ()
    {
        reveal(Anchor.TOP, null);
    }

    /** Convienence form of {@link #reveal}. */
    public void revealFromTop (Command onComplete)
    {
        reveal(Anchor.TOP, onComplete);
    }

    /** Convienence form of {@link #reveal}. */
    public void revealFromBottom ()
    {
        reveal(Anchor.BOTTOM, null);
    }

    /** Convienence form of {@link #reveal}. */
    public void revealFromBottom (Command onComplete)
    {
        reveal(Anchor.BOTTOM, onComplete);
    }

    /**
     * Hides the target widget with a vertical wipe and then removes it from its parent panel when
     * the animation completes.
     *
     * @param anchor if TOP the top of the widget will remain in place as the widget is hidden from
     * the bottom up, if BOTTOM, the bottom of the widget will move along with the wipe as if the
     * widget was being slid up off of the screen.
     * @param onComplete an optional command to execute when the animation is completd.
     */
    public void hideAndRemove (Anchor anchor, Command onComplete)
    {
        new WipeAnimation(anchor, onComplete) {
            @Override protected int computeCurHeight (double progress) {
                return (int) ((1-progress) * _targetHeight);
            }
            @Override protected void onComplete () {
                super.onComplete();
                ((Panel)getParent()).remove(RevealPanel.this);
            }
        }.run(_animTime);
    }

    /** Convienence form of {@link #hideAndRemove}. */
    public void hideAndRemoveFromTop ()
    {
        hideAndRemove(Anchor.TOP, null);
    }

    /** Convienence form of {@link #hideAndRemove}. */
    public void hideAndRemoveFromTop (Command onComplete)
    {
        hideAndRemove(Anchor.TOP, onComplete);
    }

    /** Convienence form of {@link #hideAndRemove}. */
    public void hideAndRemoveFromBottom ()
    {
        hideAndRemove(Anchor.BOTTOM, null);
    }

    /** Convienence form of {@link #hideAndRemove}. */
    public void hideAndRemoveFromBottom (Command onComplete)
    {
        hideAndRemove(Anchor.BOTTOM, onComplete);
    }

    protected abstract class WipeAnimation extends Animation
    {
        public WipeAnimation (Anchor anchor, Command onComplete) {
            _anchor = anchor;
            _onComplete = onComplete;
        }

        protected abstract int computeCurHeight (double progress);

        @Override // from Animation
        protected void onStart () {
            _targetHeight = getWidget().getOffsetHeight();
            DOM.setStyleAttribute(getElement(), "overflow", "hidden");
            if (_anchor == Anchor.BOTTOM) {
                DOM.setStyleAttribute(getWidget().getElement(), "position", "relative");
            }
            super.onStart();
        }

        @Override // from Animation
        protected void onUpdate (double progress) {
            int curHeight = computeCurHeight(progress);
            DOM.setStyleAttribute(RevealPanel.this.getElement(), "height", curHeight + "px");
            if (_anchor == Anchor.BOTTOM) {
                DOM.setStyleAttribute(
                    getWidget().getElement(), "top", (curHeight - _targetHeight) + "px");
            }
        }

        @Override // from Animation
        protected void onComplete () {
            super.onComplete();
            DOM.setStyleAttribute(getElement(), "overflow", "auto");
            DOM.setStyleAttribute(getElement(), "height", "auto");
            if (_anchor == Anchor.BOTTOM) {
                DOM.setStyleAttribute(getWidget().getElement(), "position", "static");
            }
            if (_onComplete != null) {
                _onComplete.execute();
            }
        }

        protected Anchor _anchor;
        protected Command _onComplete;
        protected int _targetHeight;
    }

    protected int _animTime;

    protected static final int DEFAULT_ANIM_TIME = 500;
}
