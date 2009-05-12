package org.deventm.syncman.database;

import java.util.Collection;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 11, 2009
 */
public interface Database {

    public abstract Collection<Device> getDevices() throws DatabaseException;

    public abstract void addPath(Device device, Path path)
	    throws DatabaseException;

    public abstract void removePath(Device device, Path path)
	    throws DatabaseException;

    public abstract void removeDevice(Device device) throws DatabaseException;

    public abstract void addDevice(Device device) throws DatabaseException;

    public abstract void flush() throws DatabaseException;

    public abstract Device getDevice(String devicestr);

}
