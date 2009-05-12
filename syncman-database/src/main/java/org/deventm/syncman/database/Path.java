package org.deventm.syncman.database;

import java.io.File;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class Path {

    final File path;

    public Path(File path) {
	this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Path) {
	    Path that = (Path) obj;
	    return path.equals(that.path);
	}
	return false;
    }

    /**
     * @return
     */
    public boolean exists() {
	return path.exists();
    }

    /**
     * @return the path
     */
    public File getPath() {
	return path;
    }

    @Override
    public int hashCode() {
	return path.hashCode();
    }

    @Override
    public String toString() {
	return path.getAbsolutePath();
    }

}
