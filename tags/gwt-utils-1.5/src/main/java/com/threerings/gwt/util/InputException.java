//
// $Id$

package com.threerings.gwt.util;

/**
 * Thrown during parsing of user input. Allows widgets or parsing methods to cleanly bail out of a
 * service call etc if the user input is incorrect.
 */
public class InputException extends RuntimeException
{
    public InputException ()
    {
    }

    public InputException (String message, Throwable cause)
    {
        super(message, cause);
    }

    public InputException (String message)
    {
        super(message);
    }

    public InputException (Throwable cause)
    {
        super(cause);
    }
}
