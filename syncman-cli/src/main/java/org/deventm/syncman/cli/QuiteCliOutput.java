package org.deventm.syncman.cli;

import org.deventm.syncman.database.Database;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class QuiteCliOutput extends CliOutput {

    /**
     * @param database
     */
    public QuiteCliOutput(Database database) {
	super(database);
    }

    /**
     * @param path
     */
    @Override
    public void outputSkip(String path) {
    }

}
