package org.deventm.syncman.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public class FileDatabase implements Database {

    /**
     * 
     */
    private static final String LOG_DATABASE_NOT_EXISTS = "Database '%s' do not exists, creating a new one.";

    /**
     * 
     */
    private static final String LOG_LOAD_PATH = "Load the path '%s' from the database.";

    /**
     * 
     */
    private static final String EXCEPTION_CREATE_DATABASE = "Can not create the database '%s'.";

    private final Logger log = Logger.getLogger(FileDatabase.class.getName());

    private final Set<File> data;

    private final File database;

    /**
     * @param database
     * @throws DatabaseException
     * 
     */
    public FileDatabase(File database) throws DatabaseException {
	this.database = database;
	data = new HashSet<File>();
	data.addAll(loadData(database));
    }

    /**
     * @param database
     * @return
     * @throws DatabaseException
     */
    private Collection<? extends File> loadData(File database)
	    throws DatabaseException {
	try {
	    return loadData0(database);
	} catch (IOException e) {
	    DatabaseException ex = new DatabaseException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(getClass().getName(), "loadData", ex);
	    throw ex;
	}
    }

    private void createDatabase(File database) throws IOException,
	    DatabaseException {
	if (!database.createNewFile()) {
	    DatabaseException ex = new DatabaseException(
		    EXCEPTION_CREATE_DATABASE.replace("%s", database
			    .getAbsolutePath()));
	    if (log.isLoggable(Level.FINER))
		log.throwing(getClass().getName(), "loadData", ex);
	    throw ex;
	}
    }

    private List<File> loadData0(File database) throws IOException,
	    DatabaseException {
	if (!database.exists()) {
	    if (log.isLoggable(Level.FINER))
		log.finer(LOG_DATABASE_NOT_EXISTS.replace("%s", database
			.getAbsolutePath()));
	    createDatabase(database);
	    return new ArrayList<File>();
	}

	BufferedReader r = new BufferedReader(new FileReader(database));
	List<File> paths = new ArrayList<File>();
	String line = null;
	while ((line = r.readLine()) != null) {
	    if (log.isLoggable(Level.FINER))
		log.finer(LOG_LOAD_PATH.replace("%s", line));
	    paths.add(new File(line));
	}

	r.close();
	return paths;
    }

    @Override
    public void addPath(File file) throws DatabaseException {
	if (data.contains(file)) {
	    return;
	}
	data.add(file);
    }

    @Override
    public Iterable<File> getPaths() throws DatabaseException {
	return data;
    }

    @Override
    public void removePath(File file) throws DatabaseException {
	data.remove(file);
    }

    @Override
    public void flush() throws DatabaseException {
	try {
	    flush0();
	} catch (IOException e) {
	    DatabaseException ex = new DatabaseException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(getClass().getName(), "flush", ex);
	    throw ex;
	}
    }

    private void flush0() throws IOException, DatabaseException {
	BufferedWriter w = new BufferedWriter(new FileWriter(database));
	for (File file : getPaths()) {
	    w.write(file.getAbsolutePath());
	    w.newLine();
	}

	w.close();
    }

}
