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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simplifies the process of validating a collection of form fields and providing validation
 * feedback.
 *
 * <p> Usage is like so:
 * <pre>{@code
 * _validator.add(_username).requireNonEmpty("Please enter your username.");
 * _validator.add(_password).requireLengthOf(6, "Enter a password of at least 6 characters.");
 * _validator.add(_agreeTOS).requireChecked("Agreement with the Terms of Service is required.");
 * // ..before submitting form...
 * if (!_validator.validate(true)) // abort!
 * }</pre></p>
 */
public class Validator
{
    /** Checks a single validation condition. */
    public interface Rule {
        /** Returns null if the condition is met, a feedback message otherwise. */
        String check ();
    }

    public class TextBoxValidator extends WidgetValidator<TextBoxValidator,TextBox> {
        public TextBoxValidator (TextBox target) {
            super(target);
            target.addBlurHandler(this);
        }

        /**
         * Requires that the text box be non-empty.
         */
        public TextBoxValidator requireNonEmpty (final String feedback) {
            return require(new Rule() {
                public String check () {
                    return (_target.getText().trim().length() == 0) ? feedback : null;
                }
            });
        }

        /**
         * Requires that the text box contain text of at least the specified length.
         */
        public TextBoxValidator requireLengthOf (final int length, final String feedback) {
            return require(new Rule() {
                public String check () {
                    return (_target.getText().trim().length() < length) ? feedback : null;
                }
            });
        }

        /**
         * Requires that the contents of this text box be equal to the contents of the supplied
         * other text box. This is useful for password/email confirmation fields.
         */
        public TextBoxValidator requireEqual (final TextBox other, final String feedback) {
            return require(new Rule() {
                public String check () {
                    return _target.getText().trim().equals(other.getText().trim()) ?
                        null : feedback;
                }
            });
        }
    }

    public class CheckBoxValidator extends WidgetValidator<CheckBoxValidator,CheckBox> {
        public CheckBoxValidator (CheckBox target) {
            super(target);
        }

        /**
         * Requires that the checkbox be checked.
         */
        public CheckBoxValidator requireChecked (final String feedback) {
            return require(new Rule() {
                public String check () {
                    return _target.getValue() ? null : feedback;
                }
            });
        }
    }

    public class IntegerBoxValidator extends WidgetValidator<IntegerBoxValidator,IntegerBox> {
        protected IntegerBoxValidator (IntegerBox target) {
            super(target);
        }

        public IntegerBoxValidator requireNonEmpty (final String feedback) {
            return require(new Rule() {
                public String check () {
                    return (_target.getText().trim().length() == 0) ? feedback : null;
                }
            });
        }

        /**
         * Requires a value greater than the provided
         */
        public IntegerBoxValidator requireGreaterThan (final int value, final String feedback) {
            return require(new Rule() {
                public String check () {
                    Integer boxValue = _target.getValue();
                    return (boxValue != null && boxValue > value) ? null : feedback;
                }
            });
        }

        /**
         * Requires a value less than the provided
         */
        public IntegerBoxValidator requireLessThan (final int value, final String feedback) {
            return require(new Rule() {
                public String check () {
                    Integer boxValue = _target.getValue();
                    return (boxValue != null && boxValue < value) ? null : feedback;
                }
            });
        }
    }

    /**
     * Returns a validator that provides rules for text boxes. The validator will automatically
     * validate the text box's contents when it loses focus.
     */
    public TextBoxValidator add (TextBox box) {
        return add(new TextBoxValidator(box));
    }

    public CheckBoxValidator add (CheckBox box) {
        return add(new CheckBoxValidator(box));
    }

    public IntegerBoxValidator add (IntegerBox box) {
        return add(new IntegerBoxValidator(box));
    }

    /**
     * Executes all registered validators (in registration order). If any validator fires a rule
     * that fails, its feedback will be displayed, the validation process will be stopped, and
     * false will be returned. If all validators succeed, true will be returned.
     */
    public boolean validate (boolean asError) {
        for (WidgetValidator<?,?> validator : _validators) {
            if (!validator.validate(asError)) return false;
        }
        return true;
    }

    protected <T extends WidgetValidator<?,?>> T add (T validator) {
        _validators.add(validator);
        return validator;
    }

    protected void displayFeedback (String feedback, Widget target, boolean asError) {
        if (_popup != null) _popup.hide();
        if (asError) _popup = Popups.error(feedback, Popups.Position.RIGHT, target);
        else _popup = Popups.info(feedback, Popups.Position.RIGHT, target);
    }

    protected abstract class WidgetValidator<S extends WidgetValidator<?,?>, T extends Widget>
        implements BlurHandler {
        public boolean validate (boolean asError) {
            for (Rule rule : _rules) {
                String feedback = rule.check();
                if (feedback != null) {
                    displayFeedback(feedback, _target, asError);
                    return false;
                }
            }
            return true;
        }

        public S require (Rule rule) {
            _rules.add(rule);
            return self();
        }

        // from interface BlurHandler
        @Override public void onBlur (BlurEvent event) {
            validate(false);
        }

        @SuppressWarnings("unchecked") protected S self () {
            return (S)this;
        }

        protected WidgetValidator (T target) {
            _target = target;
        }

        protected T _target;
        protected List<Rule> _rules = new ArrayList<Rule>();
    }

    protected List<WidgetValidator<?,?>> _validators = new ArrayList<WidgetValidator<?,?>>();
    protected InfoPopup _popup;
}
