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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * An tool task extended by I18nSync and ConstantsSync.
 */
abstract class I18nTool
{
    /**
     * Converts a single i18n properties file to its corresponding source file.
     *
     * @param sourceDir the root of the source directory. Used to infer the package for the
     * generated source given the path to the properties file and the root of the source directory.
     * @param propsFile the properties file from which to generate a source file. Name must be of
     * the form <code>X.properties</code> for any X.
     */
    public void process (File sourceDir, File propsFile)
        throws IOException
    {
        String fileName = propsFile.getName();
        if (!fileName.endsWith(".properties")) {
            System.err.println("Ignoring non-properties file: " + propsFile);
            return;
        }
        fileName = fileName.substring(0, fileName.length()-".properties".length());
        File javaFile = new File(propsFile.getParent(), fileName + ".java");
        if (propsFile.lastModified() <= javaFile.lastModified()) {
            return;
        }

        String sourcePath = sourceDir.getAbsolutePath();
        String javaPath = javaFile.getAbsolutePath();
        if (!javaPath.startsWith(sourcePath)) {
            System.err.println("Ignoring properties file outside 'srcdir': " + propsFile);
            return;
        }

        String pkg = javaPath.substring(sourcePath.length());
        if (pkg.startsWith(File.separator)) {
            pkg = pkg.substring(1);
        }
        pkg = pkg.substring(0, pkg.indexOf(javaFile.getName()));
        if (pkg.endsWith(File.separator)) {
            pkg = pkg.substring(0, pkg.length()-1);
        }
        pkg = pkg.replace(File.separatorChar, '.');

        String clazz = javaFile.getName().substring(0, javaFile.getName().indexOf(".java"));

        System.out.println("Generating " + pkg + "." + clazz + "...");
        String generated = generate(pkg, clazz, propsFile);
        PrintWriter out = new PrintWriter(javaFile, "UTF-8");
        out.print(generated);
        out.close();
    }

    /**
     * Generate the java file.
     */
    protected abstract String generate (String pkg, String clazz, File propsFile)
        throws IOException;

    /**
     * Utility for sanitizing key names.
     */
    protected String keyToMethod (String key)
    {
        return key.replace('.', '_');
    }
}
