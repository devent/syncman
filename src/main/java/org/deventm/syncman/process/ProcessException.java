package org.deventm.syncman.process;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class ProcessException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ProcessException() {
	super();
    }

    public ProcessException(String message, Throwable cause) {
	super(message, cause);
    }

    public ProcessException(String message) {
	super(message);
    }

    public ProcessException(Throwable cause) {
	super(cause);
    }

}
