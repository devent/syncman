package org.deventm.syncman.output;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class VerboseCliOutput extends CliOutput {

    /**
     * 
     */
    private static final String OUT_REMOVING_PATH = "Removing the old path '%s'.";
    /**
     * 
     */
    private static final String OUT_ADDING_PATH = "Adding the new path '%s'.";

    /**
     */
    public VerboseCliOutput() {
    }

    @Override
    public void outputAdd(String path) {
	System.out.println(OUT_ADDING_PATH.replace("%s", path));
    }

    @Override
    public void outputRemove(String path) {
	System.out.println(OUT_REMOVING_PATH.replace("%s", path));
    }

    @Override
    public void outputProcessOutput(String line) {
	System.out.println(line);
    }
}
