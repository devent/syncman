package org.deventm.syncman.output;

import java.io.File;
import java.util.Formatter;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class CliOutput {

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
     * @param path
     */
    public void outputSkip(String path) {
	System.out.println(OUT_PATH_NOT_EXISTS_SKIPPING.replace("%s", path));
    }

    /**
     * @throws DatabaseException
     * 
     */
    public void listPaths(Iterable<File> paths) {
	Formatter f = new Formatter();
	for (File path : paths) {
	    f.format("%s\n", path.getAbsolutePath());
	}

	System.out.println(f.toString());
    }

    /**
     * @param path
     */
    public void outputAdd(String path) {
    }

    /**
     * @param path
     */
    public void outputRemove(String path) {
    }

    /**
     * @param line
     */
    public void outputProcessOutput(String line) {
    }
}
