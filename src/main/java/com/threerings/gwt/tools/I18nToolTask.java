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
 * Abstract base for I18nSyncTask and ConstantsSyncTask.
 */
abstract class I18nToolTask extends Task
{
    public void addFileset (FileSet set)
    {
        _filesets.add(set);
    }

    public void setSrcdir (File srcdir)
    {
        _srcdir = srcdir;
    }

    @Override
    public void execute () throws BuildException
    {
        if (_srcdir == null) {
            throw new BuildException("Missing required attribute 'srcdir'");
        }

        I18nTool tool = createTool();
        for (FileSet fs : _filesets) {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            File fromDir = fs.getDir(getProject());
            for (String srcFile : ds.getIncludedFiles()) {
                try {
                    tool.process(_srcdir, new File(fromDir, srcFile));
                } catch (IOException ioe) {
                    throw new BuildException("Failure converting " + srcFile, ioe);
                }
            }
        }
    }

    /**
     * Create the tool we'll use for this task.
     */
    protected abstract I18nTool createTool ();

    /** The root of our source tree. */
    protected File _srcdir;

    /** A list of filesets that contain tile images. */
    protected List<FileSet> _filesets = new ArrayList<FileSet>();
}
