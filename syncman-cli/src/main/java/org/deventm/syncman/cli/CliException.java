package org.deventm.syncman.cli;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public class CliException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CliException() {
	super();
    }

    public CliException(String message, Throwable cause) {
	super(message, cause);
    }

    public CliException(String message) {
	super(message);
    }

    public CliException(Throwable cause) {
	super(cause);
    }

}
