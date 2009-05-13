package org.deventm.syncman.output;

import java.io.File;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;

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
	Formatter f = new Formatter(str);

	int devmax = 0;
	for (Device device : devices) {
	    int length = device.getDevice().getAbsolutePath().length();
	    if (devmax < length) {
		devmax = length + 3;
	    }
	}

	int pathsmax = 80 - devmax - 15 - 4;
	String fstr = "|%-" + devmax + "s|%-" + pathsmax + "s|%-15s|\n";

	for (Device device : devices) {
	    List<Path> paths = device.getPaths();
	    List<String> exs = device.getExcludes();
	    int lines = Math.max(paths.size(), exs.size());

	    f.format(fstr, "Device", "Path", "Excludes");

	    hline(str, devmax, pathsmax);

	    String path = "";
	    if (paths.size() > 0) {
		path = trimpath(paths.get(0).getPath(), pathsmax);
	    }
	    String ex = "";
	    if (exs.size() > 0) {
		ex = exs.get(0);
	    }
	    f.format(fstr, trimpath(device.getDevice(), devmax), path, ex);

	    for (int i = 1; i <= lines - 1; i++) {
		path = "";
		if (i < paths.size()) {
		    path = trimpath(paths.get(i).getPath(), pathsmax);
		}

		ex = "";
		if (i < exs.size()) {
		    ex = exs.get(i);
		}

		f.format(fstr, " ", path, ex);
	    }

	    hline(str, devmax, pathsmax);
	}

	System.out.println(str.toString());
    }

    /**
     * @param path
     * @param count
     * @return
     */
    private String trimpath(File path, int count) {
	String p = path.getAbsolutePath();
	count -= 3;
	int i = p.length() - count;
	if (i < 0) {
	    i = 0;
	}

	p = p.substring(i, p.length());
	if (i > 0) {
	    p = "..." + p;
	}
	return p;
    }

    private void hline(StringBuilder str, int devmax, int pathsmax) {
	str.append("|");
	for (int i = 0; i < devmax; i++) {
	    str.append("-");
	}
	str.append("+");
	for (int i = 0; i < pathsmax; i++) {
	    str.append("-");
	}
	str.append("+");
	for (int i = 0; i < 15; i++) {
	    str.append("-");
	}
	str.append("|");
	str.append("\n");
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

    /**
     * @param device
     * @param exstr
     */
    public void outputAddEx(Device device, String exstr) {
    }

    /**
     * @param device
     * @param exstr
     */
    public void outputRemoveEx(Device device, String exstr) {
    }
}
