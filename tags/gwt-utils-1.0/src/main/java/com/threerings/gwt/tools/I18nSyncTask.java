//
// $Id$

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
 * An Ant task to automatically generate <code>FooMessages.java</code> classes from
 * <code>FooMessages.properties</code> classes for GWT's i18n. Configure the Ant task thusly:
 * <pre>{@code
 * <taskdef name="i18nsync" classname="com.threerings.gwt.tools.I18nSyncTask"
 *          classpathref="somewhere/gwt-utils.jar"/>
 * <i18nsync srcdir="${src.dir}">}
 *   &lt;fileset dir="${src.dir}" includes="**&#47;*Messages.properties"/&gt;
 * {@code <i18nsync>}</pre>
 */
public class I18nSyncTask extends Task
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

        for (FileSet fs : _filesets) {
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());
            File fromDir = fs.getDir(getProject());
            for (String srcFile : ds.getIncludedFiles()) {
                try {
                    I18nSync.processFile(_srcdir, new File(fromDir, srcFile));
                } catch (IOException ioe) {
                    throw new BuildException("Failure converting " + srcFile, ioe);
                }
            }
        }
    }

    /** The root of our source tree. */
    protected File _srcdir;

    /** A list of filesets that contain tile images. */
    protected List<FileSet> _filesets = new ArrayList<FileSet>();
}
