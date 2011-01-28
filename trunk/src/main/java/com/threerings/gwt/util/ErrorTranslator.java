package com.threerings.gwt.util;

/**
 * Interface for translating server exceptions into localized error messages.
 */
public interface ErrorTranslator
{
    /**
     * Translates an error thrown by the server. Normally the error is deserialized during an
     * asynchronous rpc.
     */
    public String translateServerError (Throwable cause);
}
