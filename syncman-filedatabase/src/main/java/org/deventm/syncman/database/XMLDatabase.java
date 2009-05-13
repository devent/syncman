package org.deventm.syncman.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public class XMLDatabase implements Database {

    private final File database;

    private final Map<Device, Device> devices;

    private final Logger log = Logger.getLogger(XMLDatabase.class.getName());

    private final XStream xstream;

    /**
     * @param database
     * @throws DatabaseException
     * 
     */
    public XMLDatabase(File database) throws DatabaseException {
	this.database = database;
	xstream = new XStream();
	devices = loadData(database);
    }

    @Override
    public void addDevice(Device device) throws DatabaseException {
	if (devices.containsKey(device)) {
	    return;
	}
	devices.put(device, device);
    }

    @Override
    public void addPath(Device device, Path path) throws DatabaseException {
	Device dev = devices.get(device);
	if (dev == null) {
	    devices.put(device, device);
	    dev = device;
	}

	if (!dev.contains(path)) {
	    dev.paths.add(path);
	}
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

    @Override
    public Collection<Device> getDevices() throws DatabaseException {
	return devices.values();
    }

    @Override
    public void removeDevice(Device device) throws DatabaseException {
	if (!devices.containsKey(device)) {
	    return;
	}
	devices.remove(device);
    }

    @Override
    public void removePath(Device device, Path path) throws DatabaseException {
	Device dev = devices.get(device);
	if (dev == null) {
	    return;
	}

	dev.paths.remove(path);
    }

    private void flush0() throws IOException {
	OutputStream out = new FileOutputStream(database);
	xstream.toXML(devices, out);
	out.close();
    }

    /**
     * @param database
     * @return
     * @throws DatabaseException
     */
    private Map<Device, Device> loadData(File database)
	    throws DatabaseException {
	try {
	    return loadData0(database);
	} catch (FileNotFoundException e) {
	    DatabaseException ex = new DatabaseException(e);
	    if (log.isLoggable(Level.FINER))
		log.throwing(getClass().getName(), "loadData", ex);
	    throw ex;
	}
    }

    @SuppressWarnings("unchecked")
    private Map<Device, Device> loadData0(File database)
	    throws FileNotFoundException {
	if (database.exists()) {
	    FileInputStream input = new FileInputStream(database);
	    return (Map<Device, Device>) xstream.fromXML(input);
	} else {
	    return new HashMap<Device, Device>();
	}
    }

    /**
     * @param devicestr
     * @return
     */
    public Device getDevice(String devicestr) {
	Device dev = new Device(devicestr);
	return devices.get(dev);
    }

    @Override
    public void addExclude(Device device, String exstr) {
	Device dev = devices.get(device);
	if (dev == null) {
	    return;
	}

	if (!dev.excludes.contains(exstr)) {
	    dev.excludes.add(exstr);
	}
    }

    @Override
    public void removeExclude(Device device, String exstr) {
	Device dev = devices.get(device);
	if (dev == null) {
	    return;
	}

	dev.excludes.remove(exstr);
    }

}
