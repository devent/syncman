package org.deventm.syncman.cli;

import org.deventm.syncman.database.Database;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class VerboseCliOutput extends CliOutput {

    /**
     * 
     */
    private static final String OUT_REMOVING_PATH = "Removing the old path '%s'.";
    /**
     * 
     */
    private static final String OUT_ADDING_PATH = "Adding the new path '%s'.";

    /**
     * @param database
     */
    public VerboseCliOutput(Database database) {
	super(database);
    }

    @Override
    public void outputAdd(String path) {
	System.out.println(OUT_ADDING_PATH.replace("%s", path));
    }

    @Override
    public void outputRemove(String path) {
	System.out.println(OUT_REMOVING_PATH.replace("%s", path));
    }
}
