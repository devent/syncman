package org.deventm.syncman.cli;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.deventm.syncman.database.DatabaseException;
import org.deventm.syncman.database.FileDatabase;
import org.deventm.syncman.output.CliOutput;
import org.deventm.syncman.process.ProcessController;
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

    private static final String HOME = System.getProperty("user.home");

    private final Logger log = Logger.getLogger(CliController.class.getName());

    private final FileDatabase database;

    private final ParamsParser parser;

    private final CliOutput output;

    /**
     * @param database
     * @param parser
     */
    public CliController(FileDatabase database, ParamsParser parser,
	    CliOutput output) {
	this.database = database;
	this.parser = parser;
	this.output = output;
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
	    output.listPaths(database.getPaths());
	}
	if (parser.isSync()) {
	    startSync();
	}
    }

    /**
     * @throws DatabaseException
     * 
     */
    private void startSync() throws DatabaseException {
	for (String name : parser.getDevices()) {
	    File device = new File(name);
	    ProcessController c = new ProcessController(output, database
		    .getPaths(), device);
	    c.startSync();
	}
    }

    /**
     * @throws DatabaseException
     * 
     */
    private void removePaths() throws DatabaseException {
	for (String path : parser.getAdds()) {
	    File file = new File(path);
	    output.outputRemove(file.getAbsolutePath());
	    database.removePath(file);
	}
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
		output.outputSkip(path);
		continue;
	    }
	    output.outputAdd(path);
	    database.addPath(file);
	}
    }

}
