package org.deventm.syncman.database;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public class DatabaseException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DatabaseException() {
	super();
    }

    public DatabaseException(String message, Throwable cause) {
	super(message, cause);
    }

    public DatabaseException(String message) {
	super(message);
    }

    public DatabaseException(Throwable cause) {
	super(cause);
    }

}
