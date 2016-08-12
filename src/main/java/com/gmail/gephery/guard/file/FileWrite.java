package com.gmail.gephery.guard.file;

import com.gmail.gephery.teleport.buffers.FileBuffer;
import net.projectzombie.survivalteams.file.WorldCoordinate;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by maxgr on 7/15/2016.
 */
public class FileWrite {

    public static boolean setCoordinates(String region, World world, Location[] locs) {
        FileBuffer.loadFile(world);
        String[] paths = FilePath.getCoordinatesPaths(region);
        FileBuffer.file.set(paths[0], WorldCoordinate.toString(locs[0].getBlock()));
        FileBuffer.file.set(paths[1], WorldCoordinate.toString(locs[1].getBlock()));

        return FileBuffer.saveFiles();
    }

    public static boolean setAttributeValue(String region, World world, String attribute, String attributeValue) {
        FileBuffer.loadFile(world);
        String path = FilePath.getAttributePath(region, attribute);
        FileBuffer.file.set(path, attributeValue);

        return FileBuffer.saveFiles();
    }

}
