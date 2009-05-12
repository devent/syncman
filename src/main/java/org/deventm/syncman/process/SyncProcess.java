package org.deventm.syncman.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class SyncProcess implements Runnable {

    private final Logger log = Logger.getLogger(SyncProcess.class.getName());

    private static final String COMMAND_RSYNC = "/usr/bin/rsync";

    private Exception exception;

    private InputStream output;

    private InputStream error;

    private int ret;

    private final String params;

    private final File path;

    private final File device;

    public SyncProcess(String params, File path, File device) {
	this.params = params;
	this.path = path;
	this.device = device;
    }

    @Override
    public void run() {
	try {
	    run0();
	} catch (IOException e) {
	    if (log.isLoggable(Level.FINER))
		log.throwing(getClass().getName(), "run", e);
	    exception = e;
	} catch (InterruptedException e) {
	    if (log.isLoggable(Level.FINER))
		log.throwing(getClass().getName(), "run", e);
	    exception = e;
	}
    }

    /**
     * @return the error
     */
    public InputStream getError() {
	return error;
    }

    /**
     * @return the output
     */
    public InputStream getOutput() {
	return output;
    }

    /**
     * @return the ret
     */
    public int getRet() {
	return ret;
    }

    /**
     * @return the exception
     */
    public Exception getException() {
	return exception;
    }

    private void run0() throws IOException, InterruptedException {
	String command = COMMAND_RSYNC;

	Formatter f = new Formatter();
	f.format("%s %s %s %s", command, params, path.getAbsolutePath(), device
		.getAbsolutePath());

	Process p = Runtime.getRuntime().exec(command);
	output = p.getInputStream();
	error = p.getErrorStream();
	ret = p.waitFor();
    }

}
