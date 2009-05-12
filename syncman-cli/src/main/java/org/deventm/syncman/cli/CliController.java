package org.deventm.syncman.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.deventm.syncman.database.DatabaseException;
import org.deventm.syncman.database.Device;
import org.deventm.syncman.database.Path;
import org.deventm.syncman.database.XMLDatabase;
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

    private static final String LOG_DEVICE_NOT_EXISTS_SKIPPING = "Device '%s' do not exists, skipping.";

    private final Logger log = Logger.getLogger(CliController.class.getName());

    private final XMLDatabase database;

    private final ParamsParser parser;

    private final CliOutput output;

    /**
     * @param database
     * @param parser
     */
    public CliController(XMLDatabase database, ParamsParser parser,
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
	    output.listPaths(database.getDevices());
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
	for (String devicestr : parser.getDevices()) {
	    devicestr = devicestr.replaceFirst("^~", HOME);
	    Device device = database.getDevice(devicestr);
	    if (device == null) {
		continue;
	    }

	    ProcessController c = new ProcessController(output, device);
	    c.startSync();
	}
    }

    /**
     * @throws DatabaseException
     * 
     */
    private void removePaths() throws DatabaseException {
	for (String devicestr : parser.getDevices()) {
	    devicestr = devicestr.replaceFirst("^~", HOME);
	    Device device = new Device(new ArrayList<Path>(), new File(
		    devicestr));
	    for (String pathstr : parser.getRemoves()) {
		pathstr = pathstr.replaceFirst("^~", HOME);
		Path path = new Path(new File(pathstr));
		output.outputRemove(device, path);
		database.removePath(device, path);
	    }
	}
    }

    /**
     * @throws DatabaseException
     * 
     */
    private void addPaths() throws DatabaseException {
	for (String devicestr : parser.getDevices()) {
	    devicestr = devicestr.replaceFirst("^~", HOME);
	    Device device = new Device(new ArrayList<Path>(), new File(
		    devicestr));
	    if (!device.exists()) {
		if (log.isLoggable(Level.WARNING))
		    log.warning(LOG_DEVICE_NOT_EXISTS_SKIPPING.replace("%s",
			    device.toString()));
		output.outputSkip(device);
		continue;
	    }

	    for (String pathstr : parser.getAdds()) {
		pathstr = pathstr.replaceFirst("^~", HOME);
		Path path = new Path(new File(pathstr));

		if (!path.exists()) {
		    if (log.isLoggable(Level.WARNING))
			log.warning(LOG_PATH_NOT_EXISTS_SKIPPING.replace("%s",
				path.toString()));
		    output.outputSkip(path);
		    continue;
		}

		output.outputAdd(device, path);
		database.addPath(device, path);
	    }
	}
    }

}
