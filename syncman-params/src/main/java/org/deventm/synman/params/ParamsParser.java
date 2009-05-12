package org.deventm.synman.params;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.deventm.cli.LineParser;
import org.deventm.cli.Option;
import org.deventm.cli.OptionParser;
import org.deventm.cli.Option.HasArgs;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public class ParamsParser extends LineParser {

    private static final String LONG_ADD = "add";

    private static final String LONG_DEVICE = "device";

    private static final String LONG_LIST = "list";

    private static final String LONG_QUITE = "quite";

    private static final String LONG_REMOVE = "remove";

    private static final String LONG_SYNC = "sync";

    private static final String LONG_VERBOSE = "verbose";

    private static final String SHORT_ADD = "a";

    private static final String SHORT_DEVICE = "d";

    private static final String SHORT_LIST = "l";

    private static final String SHORT_QUITE = "q";

    private static final String SHORT_REMOVE = "r";

    private static final String SHORT_SYNC = "s";

    private static final String SHORT_VERBOSE = "v";

    private static final String VAL_ADDS_DEVICES = "To add a new path you need to specify the device.";

    private static final String VAL_REMOVE_DEVICES = "To remove a path you need to specify the device.";

    private static final String VAL_SYNC_DEVICES = "If you want to start the synchronization you must specify at least one device.";

    private static final String VAL_VERBOSE_QUITE = "The application cannot be verbose and quite at the same time.";

    private final List<String> adds;

    private final List<String> devices;

    private boolean list;

    private final Logger log = Logger.getLogger(ParamsParser.class.getName());

    private boolean quite;

    private final List<String> removes;

    private boolean sync;

    private boolean verbose;

    /**
     * @param args
     * 
     */
    public ParamsParser(String[] args) {
	super(args);

	adds = new ArrayList<String>();
	devices = new ArrayList<String>();
	removes = new ArrayList<String>();
	list = false;
	quite = false;
	sync = false;
	verbose = false;

	addOption(new Option(SHORT_DEVICE, LONG_DEVICE, "", HasArgs.YES,
		new OptionParser() {

		    @Override
		    public void parse(CommandLine line) {
			parseDevice(line.getOptionValue(SHORT_DEVICE));
		    }
		}));
	addOption(new Option(SHORT_SYNC, LONG_SYNC, "", HasArgs.NO,
		new OptionParser() {

		    @Override
		    public void parse(CommandLine line) {
			parseSync("true");
		    }
		}));
	addOption(new Option(SHORT_VERBOSE, LONG_VERBOSE, "", HasArgs.NO,
		new OptionParser() {

		    @Override
		    public void parse(CommandLine line) {
			parseVerbose("true");
		    }
		}));
	addOption(new Option(SHORT_QUITE, LONG_QUITE, "", HasArgs.NO,
		new OptionParser() {

		    @Override
		    public void parse(CommandLine line) {
			parseQuite("true");
		    }
		}));
	addOption(new Option(SHORT_ADD, LONG_ADD, "", HasArgs.YES,
		new OptionParser() {

		    @Override
		    public void parse(CommandLine line) {
			parseAdd(line.getOptionValue(SHORT_ADD));
		    }
		}));
	addOption(new Option(SHORT_REMOVE, LONG_REMOVE, "", HasArgs.YES,
		new OptionParser() {

		    @Override
		    public void parse(CommandLine line) {
			parseRemove(line.getOptionValue(SHORT_REMOVE));
		    }
		}));
	addOption(new Option(SHORT_LIST, LONG_LIST, "", HasArgs.NO,
		new OptionParser() {

		    @Override
		    public void parse(CommandLine line) {
			parseList("true");
		    }
		}));
    }

    /**
     * @return the adds
     */
    public List<String> getAdds() {
	return new ArrayList<String>(adds);
    }

    /**
     * @return the devices
     */
    public List<String> getDevices() {
	return new ArrayList<String>(devices);
    }

    /**
     * @return the removes
     */
    public List<String> getRemoves() {
	return removes;
    }

    /**
     * @return the list
     */
    public boolean isList() {
	return list;
    }

    /**
     * @return the quite
     */
    public boolean isQuite() {
	return quite;
    }

    /**
     * @return the sync
     */
    public boolean isSync() {
	return sync;
    }

    /**
     * @return the verbose
     */
    public boolean isVerbose() {
	return verbose;
    }

    public String validate() {
	if (adds.size() > 0 && devices.size() == 0) {
	    return VAL_ADDS_DEVICES;
	}
	if (removes.size() > 0 && devices.size() == 0) {
	    return VAL_REMOVE_DEVICES;
	}
	if (sync && devices.size() == 0) {
	    return VAL_SYNC_DEVICES;
	}
	if (verbose && quite) {
	    return VAL_VERBOSE_QUITE;
	}

	return null;
    }

    /**
     * @param optionValue
     */
    private void parseAdd(String optionValue) {
	if (optionValue == null) {
	    return;
	}

	if (log.isLoggable(Level.CONFIG))
	    log.config("set add to '" + optionValue + "'.");

	adds.add(optionValue);
    }

    /**
     * @param optionValue
     */
    private void parseDevice(String optionValue) {
	if (optionValue == null) {
	    return;
	}

	if (log.isLoggable(Level.CONFIG))
	    log.config("set device to '" + optionValue + "'.");

	devices.add(optionValue);
    }

    /**
     * @param optionValue
     */
    private void parseList(String optionValue) {
	if (optionValue == null) {
	    return;
	}

	if (log.isLoggable(Level.CONFIG))
	    log.config("set list to '" + optionValue + "'.");

	list = Boolean.valueOf(optionValue);
    }

    /**
     * @param optionValue
     */
    private void parseQuite(String optionValue) {
	if (optionValue == null) {
	    return;
	}

	if (log.isLoggable(Level.CONFIG))
	    log.config("set quite to '" + optionValue + "'.");

	quite = Boolean.valueOf(optionValue);
    }

    /**
     * @param optionValue
     */
    private void parseRemove(String optionValue) {
	if (optionValue == null) {
	    return;
	}

	if (log.isLoggable(Level.CONFIG))
	    log.config("set remove to '" + optionValue + "'.");

	removes.add(optionValue);
    }

    /**
     * @param optionValue
     */
    private void parseSync(String optionValue) {
	if (optionValue == null) {
	    return;
	}

	if (log.isLoggable(Level.CONFIG))
	    log.config("set sync to '" + optionValue + "'.");

	sync = Boolean.valueOf(optionValue);
    }

    /**
     * @param optionValue
     */
    private void parseVerbose(String optionValue) {
	if (optionValue == null) {
	    return;
	}

	if (log.isLoggable(Level.CONFIG))
	    log.config("set verbose to '" + optionValue + "'.");

	verbose = Boolean.valueOf(optionValue);
    }
}
