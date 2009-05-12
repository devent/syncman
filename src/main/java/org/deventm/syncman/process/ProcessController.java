package org.deventm.syncman.process;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /**
     * 
     */
    public ProcessController(Iterable<File> paths, File device) {
	this.paths = paths;
	this.device = device;

	params = "-compress --recursive --delete --links --times --perms";
	executor = Executors.newFixedThreadPool(2);
    }

    public void startSync() {
	for (File path : paths) {
	    executor.execute(new SyncProcess(params, path, device));
	}
    }
}
