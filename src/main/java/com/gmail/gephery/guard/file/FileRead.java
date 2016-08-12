package com.gmail.gephery.guard.file;

import com.gmail.gephery.teleport.buffers.FileBuffer;
import net.projectzombie.survivalteams.file.WorldCoordinate;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;
import java.util.Set;

/**
 * Created by maxgr on 7/15/2016.
 */
public class FileRead {

    public static Location[] getCoordinates(String region, World world) {
        FileBuffer.loadFile(world);
        Location[] locs = new Location[2];
        String path1 = FilePath.getCoordinatesPaths(region)[0];
        String path2 = FilePath.getCoordinatesPaths(region)[1];
        if (FileBuffer.isSafePath(path1) && FileBuffer.isSafePath(path2)) {
            locs[0] = WorldCoordinate.toLocation(FileBuffer.file.getString(path1));
            locs[1] = WorldCoordinate.toLocation(FileBuffer.file.getString(path2));
            return locs;
        }

        return null;
    }

    public static List<String> getAttributes(String region, World world) {
        FileBuffer.loadFile(world);
        String path = FilePath.getAttributesPath(region);
        if (FileBuffer.isSafePath(path)) {
            return FileBuffer.file.getStringList(path);
        }

        return null;
    }

    public static String getAttributeValue(String region, World world, String attribute) {
        FileBuffer.loadFile(world);
        String path = FilePath.getAttributePath(region, attribute);
        if (FileBuffer.isSafePath(path)) {
            return FileBuffer.file.getString(path);
        }

        return null;
    }

    public static Set<String> getRegions(World world) {
        FileBuffer.loadFile(world);
        String path = FilePath.ROOT;
        if (FileBuffer.isSafePath(path)) {
            return FileBuffer.file.getConfigurationSection("region").getKeys(false);
        }

        return null;
    }
}
