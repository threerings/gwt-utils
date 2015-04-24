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

package com.threerings.gwt.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * An Ant task to automatically generate <code>FooConstants.java</code> classes from
 * <code>FooConstants.properties</code> classes for GWT's i18n. Configure the Ant task thusly:
 * <pre>{@code
 * <taskdef name="i18nsync" classname="com.threerings.gwt.tools.ConstantsSyncTask"
 *          classpathref="somewhere/gwt-utils.jar"/>
 * <i18nsync srcdir="${src.dir}">}
 *   &lt;fileset dir="${src.dir}" includes="**&#47;*Constants.properties"/&gt;
 * {@code <i18nsync>}</pre>
 */
public class ConstantsSyncTask extends I18nToolTask
{
    @Override
    protected ConstantsSync createTool ()
    {
        return new ConstantsSync();
    }
}
