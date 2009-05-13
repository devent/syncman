package org.deventm.syncman.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Erwin Mueller &lt;funnyacc@gmx.net&gt;
 * @since May 12, 2009
 */
public class Device {

    private static final String HOME = System.getProperty("user.home");

    final File device;

    final List<String> excludes;

    final List<Path> paths;

    public Device(List<Path> paths, File device, List<String> excludes) {
	this.paths = paths;
	this.device = device;
	this.excludes = excludes;
    }

    public Device(List<Path> paths, String devicestr, List<String> excludes) {
	devicestr = devicestr.replaceFirst("^~", HOME);
	device = new File(devicestr);
	this.paths = paths;
	this.excludes = excludes;
    }

    /**
     * @param devicestr
     * 
     */
    public Device(String devicestr) {
	this(new ArrayList<Path>(), devicestr, new ArrayList<String>());
    }

    /**
     * @param path
     * @return
     */
    public boolean contains(Path path) {
	return paths.contains(path);
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
     * @return
     */
    public boolean exists() {
	return device.exists();
    }

    /**
     * @return the device
     */
    public File getDevice() {
	return device;
    }

    /**
     * @return the excludes
     */
    public List<String> getExcludes() {
	return excludes;
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
}
