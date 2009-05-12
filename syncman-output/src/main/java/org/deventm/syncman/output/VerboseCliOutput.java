package org.deventm.syncman.output;

import java.util.Formatter;

import org.deventm.syncman.database.Device;
import org.deventm.syncman.database.Path;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class VerboseCliOutput extends CliOutput {

    /**
     * 
     */
    private static final String OUT_REMOVING_PATH = "Removing the path '%s' from the device '%s'.";
    /**
     * 
     */
    private static final String OUT_ADDING_PATH = "Adding the path '%s' to the device '%s'.";

    /**
     */
    public VerboseCliOutput() {
    }

    @Override
    public void outputAdd(Device device, Path path) {
	Formatter f = new Formatter();
	f.format(OUT_ADDING_PATH, path.toString(), device.toString());
	System.out.println(f.toString());
    }

    @Override
    public void outputRemove(Device device, Path path) {
	Formatter f = new Formatter();
	f.format(OUT_REMOVING_PATH, path.toString(), device.toString());
	System.out.println(f.toString());
    }

    @Override
    public void outputProcessOutput(String line) {
	System.out.println(line);
    }
}
