package com.gmail.gephery.guard.file;

import org.bukkit.World;

/**
 * Created by maxgr on 7/15/2016.
 */
public class FilePath {

    public static final String ROOT = "region";

    public static String getFileFolder(World world) {
        return "/" + world.getUID() + "/";
    }

    public static String getRegionPath(String region) {
        return ROOT + "." + region;
    }

    public static String getAttributesPath(String region) {
        return getRegionPath(region) + "." + "attributes";
    }

    public static String getAttributePath(String region, String attribute) {
        return getAttributesPath(region) + "." + attribute;
    }

    /**
     * Builds a String array of paths to region coordinates, in order of
     * point 1 point 2
     */
    public static String[] getCoordinatesPaths(String region) {
        String[] coordinates = {getRegionPath(region) + ".loc1",
                                getRegionPath(region) + ".loc2"};

        return coordinates;
    }
}
