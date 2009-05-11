package org.deventm.syncman.cli;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.commons.cli.ParseException;
import org.deventm.synman.params.ParamsParser;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public class Start {

    private static final Logger log = Logger.getLogger(Start.class.getName());

    /**
     * 
     */
    private static final String LOG_EXIT_ERROR = "Exit with error '%s'.";

    private static final String LOG_PROPERTIES_FILE = "syncmanlog.properties";

    private static final String PACKAGE_URL = "/org/deventm/regexptest";

    private static final String PACKAGENAME = "org.deventm.syncman";

    private static final String PROPFILENAME = "syncman.properties";

    private static final String LOG_EXIT_DONE = "Done without errors.";

    public static void main(String[] args) throws CliException {
	loadLoggerProperties();
	ParamsParser parser = createParser(args);
	String val = parser.validate();
	if (val != null) {
	    System.out.println(val);
	    exit(LOG_EXIT_ERROR.replace("%s", val), 1);
	}

	CliController controller = new CliController(parser);

	exit(LOG_EXIT_DONE, 0);
    }

    /**
     * @param args
     * @return
     * @throws CliException
     */
    private static ParamsParser createParser(String[] args) throws CliException {
	try {
	    return ParamsParser.loadParser(new ParamsParser(args), PACKAGENAME,
		    PROPFILENAME);
	} catch (IOException e) {
	    CliException ex = new CliException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(Start.class.getName(), "createParser", ex);
	    throw ex;
	} catch (ParseException e) {
	    CliException ex = new CliException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(Start.class.getName(), "createParser", ex);
	    throw ex;
	}
    }

    private static void exit(String str, int status) {
	if (log.isLoggable(Level.INFO))
	    log.info(str);
	System.exit(status);
    }

    private static void loadLoggerProperties() throws CliException {
	try {
	    loadLoggerProperties0();
	} catch (MalformedURLException e) {
	    CliException ex = new CliException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(Start.class.getName(), "loadLoggerProperties", ex);
	    throw ex;
	} catch (IOException e) {
	    CliException ex = new CliException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(Start.class.getName(), "loadLoggerProperties", ex);
	    throw ex;
	}
    }

    private static void loadLoggerProperties0() throws MalformedURLException,
	    IOException {
	String logres = Start.PACKAGE_URL + "/" + LOG_PROPERTIES_FILE;
	URL url = Start.class.getResource(logres);
	File file = new File(LOG_PROPERTIES_FILE);
	if (file.isFile()) {
	    url = file.toURI().toURL();
	}
	if (url != null) {
	    LogManager.getLogManager().readConfiguration(url.openStream());
	}
    }

}
