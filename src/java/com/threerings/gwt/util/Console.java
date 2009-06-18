//
// $Id$

package com.threerings.gwt.util;

import com.google.gwt.core.client.GWT;

/**
 * Provides console related routines (presently just logging).
 */
public class Console
{
    /**
     * Formats and logs a message. If we are running in HostedMode the log message will be reported
     * to its console. If we're running in Firefox, the log message will be sent to Firebug if it
     * is enabled.
     */
    public static void log (String message, Object... args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        if (args.length > 1) {
            sb.append(" [");
            for (int ii = 0, ll = args.length/2; ii < ll; ii++) {
                if (ii > 0) {
                    sb.append(", ");
                }
                sb.append(args[2*ii]).append("=").append(args[2*ii+1]);
            }
            sb.append("]");
        }
        Object error = (args.length % 2 == 1) ? args[args.length-1] : null;
        if (GWT.isScript()) {
            if (error != null) {
                sb.append(": ").append(error);
            }
            firebugLog(sb.toString(), error);
        } else {
            GWT.log(sb.toString(), (Throwable)error);
        }
    }

    /**
     * Records a log message to the JavaScript console via Firebug.
     */
    protected static native void firebugLog (String message, Object error) /*-{
        if ($wnd.console) {
            if (error != null) {
                $wnd.console.info(message, error);
            } else {
                $wnd.console.info(message);
            }
        }
    }-*/;
}
