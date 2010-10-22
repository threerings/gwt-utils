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
     * Prints out the stack trace of the caller. Note this will only work on Firebug.
     * TODO: print via the {@link #log} method instead of using $wnd.console
     * See http://eriwen.com/javascript/js-stack-trace/
     */
    public static native void printStackTrace () /*-{
        var callstack = [];
        var isCallstackPopulated = false;
        try {
            i.dont.exist+=0; //doesn't exist- that's the point
        } catch(e) {
            if (e.stack) { //Firefox
                var lines = e.stack.split("\n");
                for (var i=0, len=lines.length; i<len; i++) {
                    if (lines[i].match(/^\s*[A-Za-z0-9\-_\$]+\(/)) {
                        callstack.push(lines[i]);
                    }
                }

                //Remove call to printStackTrace()
                callstack.shift();
                isCallstackPopulated = true;
            }
            else if ($wnd.opera && e.message) { //Opera
                var lines = e.message.split("\n");
                for (var i=0, len=lines.length; i<len; i++) {
                    if (lines[i].match(/^\s*[A-Za-z0-9\-_\$]+\(/)) {
                        var entry = lines[i];
                        //Append next line also since it has the file info
                        if (lines[i+1]) {
                            entry += " at " + lines[i+1];
                            i++;
                        }
                        callstack.push(entry);
                    }
                }
                //Remove call to printStackTrace()
                callstack.shift();
                isCallstackPopulated = true;
            }
        }
        if (!isCallstackPopulated) { //IE and Safari
            var currentFunction = arguments.callee.caller;
            while (currentFunction) {
                var fn = currentFunction.toString();
                var fname = fn.substring(fn.indexOf("function") + 8,
                                         fn.indexOf("(")) || "anonymous";
                callstack.push(fname);
                currentFunction = currentFunction.caller;
            }
        }
        for (var i = 0; i < callstack.length; ++i) {
            if ($wnd.console) {
                $wnd.console.info("   Frame " + i + ": " + callstack[i]);
            }
        }
    }-*/;

    /**
     * Records a log message to the JavaScript console via Firebug.
     * TODO: make this work with other browsers.
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
