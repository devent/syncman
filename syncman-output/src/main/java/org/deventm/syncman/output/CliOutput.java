package org.deventm.syncman.output;

import java.util.Collection;

import org.deventm.syncman.database.DatabaseException;
import org.deventm.syncman.database.Device;
import org.deventm.syncman.database.Path;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class CliOutput {

    private static final String OUT_DEVICE_NOT_EXISTS_SKIPPING = "Device '%s' do not exists, skipping.";
    /**
     * 
     */
    private static final String OUT_PATH_NOT_EXISTS_SKIPPING = "Path '%s' do not exists, skipping.";

    /**
     * 
     */
    public CliOutput() {
    }

    /**
     * @throws DatabaseException
     * 
     */
    public void listPaths(Collection<Device> devices) {
	StringBuilder str = new StringBuilder();
	for (Device device : devices) {
	    str.append(device.toString());
	    str.append(": ");
	    for (Path path : device.getPaths()) {
		str.append(path.toString());
		str.append("\n");
	    }
	}

	System.out.println(str.toString());
    }

    /**
     * @param device
     * @param path
     */
    public void outputAdd(Device device, Path path) {
    }

    /**
     * @param line
     */
    public void outputProcessOutput(String line) {
    }

    /**
     * @param device
     * @param path
     */
    public void outputRemove(Device device, Path path) {
    }

    /**
     * @param device
     */
    public void outputSkip(Device device) {
	System.out.println(OUT_DEVICE_NOT_EXISTS_SKIPPING.replace("%s", device
		.toString()));
    }

    /**
     * @param path
     */
    public void outputSkip(Path path) {
	System.out.println(OUT_PATH_NOT_EXISTS_SKIPPING.replace("%s", path
		.toString()));
    }
}
