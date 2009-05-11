package org.deventm.syncman.cli;

import java.io.File;
import java.util.Formatter;

import org.deventm.syncman.database.Database;
import org.deventm.syncman.database.DatabaseException;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class CliOutput {

    /**
     * 
     */
    private static final String OUT_PATH_NOT_EXISTS_SKIPPING = "Path '%s' do not exists, skipping.";

    private final Database database;

    /**
     * 
     */
    public CliOutput(Database database) {
	this.database = database;
    }

    /**
     * @param path
     */
    public void outputSkip(String path) {
	System.out.println(OUT_PATH_NOT_EXISTS_SKIPPING.replace("%s", path));
    }

    /**
     * @throws DatabaseException
     * 
     */
    public void listPaths() throws DatabaseException {
	Formatter f = new Formatter();
	for (File path : database.getPaths()) {
	    f.format("%s\n", path.getAbsolutePath());
	}

	System.out.println(f.toString());
    }

    /**
     * @param path
     */
    public void outputAdd(String path) {
    }

    /**
     * @param path
     */
    public void outputRemove(String path) {
    }
}
