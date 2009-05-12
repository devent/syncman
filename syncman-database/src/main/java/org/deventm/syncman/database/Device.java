package org.deventm.syncman.database;

import java.io.File;
import java.util.List;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class Device {

    final File device;

    final List<Path> paths;

    public Device(List<Path> paths, File device) {
	this.paths = paths;
	this.device = device;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof Device) {
	    Device that = (Device) obj;
	    return device.equals(that.device);
	}
	return false;
    }

    /**
     * @return the device
     */
    public File getDevice() {
	return device;
    }

    /**
     * @return the paths
     */
    public List<Path> getPaths() {
	return paths;
    }

    @Override
    public int hashCode() {
	return device.hashCode();
    }

    @Override
    public String toString() {
	return device.getAbsolutePath();
    }

    /**
     * @return
     */
    public boolean exists() {
	return device.exists();
    }

    /**
     * @param path
     * @return
     */
    public boolean contains(Path path) {
	return paths.contains(path);
    }
}
