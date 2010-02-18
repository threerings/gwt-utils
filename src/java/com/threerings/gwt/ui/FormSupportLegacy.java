//
// $Id$

package com.threerings.gwt.ui;

import com.google.gwt.user.client.ui.TextBoxBase;

/**
 * Form support for older browsers that don't support HTML5 form awesomeness.
 */
class FormSupportLegacy extends FormSupport
{
    @Override
    @SuppressWarnings("deprecation")
    public <B extends TextBoxBase> B setPlaceholderText (B box, String placeholder)
    {
        DefaultTextListener.configure(box, placeholder);
        return box;
    }
}
