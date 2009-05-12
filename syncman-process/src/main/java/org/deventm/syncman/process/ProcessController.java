package org.deventm.syncman.process;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.deventm.syncman.output.CliOutput;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class ProcessController {

    private final Iterable<File> paths;

    private final File device;

    private final ExecutorService executor;

    private final String params;

    private final CliOutput output;

    /**
     * 
     */
    public ProcessController(CliOutput output, Iterable<File> paths, File device) {
	this.output = output;
	this.paths = paths;
	this.device = device;

	params = "-compress --recursive --delete --links --times --perms";
	executor = Executors.newFixedThreadPool(2);
    }

    public void startSync() {
	for (File path : paths) {
	    executor.execute(new SyncProcess(output, params, path, device));
	}
    }
}
