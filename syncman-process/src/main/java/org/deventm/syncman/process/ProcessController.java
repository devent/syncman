package org.deventm.syncman.process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.deventm.syncman.database.Device;
import org.deventm.syncman.database.Path;
import org.deventm.syncman.output.CliOutput;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class ProcessController {

    private final Device device;

    private final ExecutorService executor;

    private final String params;

    private final CliOutput output;

    /**
     * @param output
     * @param device
     */
    public ProcessController(CliOutput output, Device device) {
	this.output = output;
	this.device = device;

	params = "--compress --recursive --delete --links --times --perms -P";
	executor = Executors.newFixedThreadPool(2);
    }

    public void startSync() {
	for (Path path : device.getPaths()) {
	    executor.execute(new SyncProcess(output, params, path.getPath(),
		    device));
	}
    }
}
