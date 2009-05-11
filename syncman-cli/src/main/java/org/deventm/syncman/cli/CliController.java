package org.deventm.syncman.cli;

import java.io.File;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.deventm.syncman.database.DatabaseException;
import org.deventm.syncman.database.FileDatabase;
import org.deventm.synman.params.ParamsParser;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public class CliController {

    /**
     * 
     */
    private static final String LOG_PATH_NOT_EXISTS_SKIPPING = "Path '%s' do not exists, skipping.";

    /**
     * 
     */
    private static final String OUT_PATH_NOT_EXISTS_SKIPPING = "Path '%s' do not exists, skipping.";

    private static final String HOME = System.getProperty("user.home");

    private final Logger log = Logger.getLogger(CliController.class.getName());

    private final FileDatabase database;

    private final ParamsParser parser;

    /**
     * @param database
     * @param parser
     */
    public CliController(FileDatabase database, ParamsParser parser) {
	this.database = database;
	this.parser = parser;
    }

    public void run() throws CliException {
	try {
	    run0();
	} catch (DatabaseException e) {
	    CliException ex = new CliException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(getClass().getName(), "run", ex);
	    throw ex;
	}
    }

    private void run0() throws DatabaseException {
	addPaths();
	removePaths();
	if (parser.isList()) {
	    listPaths();
	}
	if (parser.isSync()) {
	    startSync();
	}
    }

    /**
     * 
     */
    private void startSync() {
	// TODO Auto-generated method stub

    }

    /**
     * @throws DatabaseException
     * 
     */
    private void removePaths() throws DatabaseException {
	for (String path : parser.getAdds()) {
	    File file = new File(path);
	    database.removePath(file);
	}
    }

    /**
     * @param path
     */
    private void outputSkip(String path) {
	if (parser.isQuite()) {
	    return;
	}
	System.out.println(OUT_PATH_NOT_EXISTS_SKIPPING.replace("%s", path));
    }

    /**
     * @throws DatabaseException
     * 
     */
    private void addPaths() throws DatabaseException {
	for (String path : parser.getAdds()) {
	    path = path.replaceFirst("^~", HOME);
	    File file = new File(path);
	    if (!file.exists()) {
		if (log.isLoggable(Level.WARNING))
		    log.warning(LOG_PATH_NOT_EXISTS_SKIPPING
			    .replace("%s", path));
		outputSkip(path);
		continue;
	    }
	    database.addPath(file);
	}
    }

    /**
     * @throws DatabaseException
     * 
     */
    private void listPaths() throws DatabaseException {
	Formatter f = new Formatter();
	for (File path : database.getPaths()) {
	    f.format("%s\n", path.getAbsolutePath());
	}

	System.out.println(f.toString());
    }
}
