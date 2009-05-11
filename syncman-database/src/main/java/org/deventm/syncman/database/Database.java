package org.deventm.syncman.database;

import java.io.File;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public interface Database {

    public abstract Iterable<File> getPaths() throws DatabaseException;

    public abstract void addPath(File file) throws DatabaseException;

    public abstract void removePath(File file) throws DatabaseException;

    public abstract void flush() throws DatabaseException;
}
