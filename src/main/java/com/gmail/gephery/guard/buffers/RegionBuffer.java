package com.gmail.gephery.guard.buffers;

import com.gmail.gephery.guard.file.FileRead;
import net.projectzombie.survivalteams.file.WorldCoordinate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by maxgr on 7/15/2016.
 */
public class RegionBuffer {

    private static Map<UUID, Set<String>> regions;
    private static Map<String, String> pToInner;

    public RegionBuffer() {
        regions = new HashMap<UUID, Set<String>>();
        pToInner = new HashMap<String, String>();
    }

    public static Set<String> getRegions(World world) {
        loadRegions(world);
        return regions.get(world.getUID());
    }

    public static boolean isRegionsLoaded(World world) {
        return regions.get(world.getUID()) != null;
    }

    /**
     * Description: Loads the two files, user.yml and group.yml
     */
    public static boolean loadRegions(World world) {
        if (!isRegionsLoaded(world)) {
            reloadRegions(world);
        }

        return regions.get(world.getUID()) != null;
    }

    public static void reloadRegions(World world) {
        regions.put(world.getUID(), FileRead.getRegions(world));
    }

    public static boolean checkIfInRegion(Block block, World world, String region) {
        Location[] locs = FileRead.getCoordinates(region, world);
        if (locs != null && locs[0] != null && locs[1] != null) {
            if ((locs[0].getBlockX() >= block.getX() && locs[1].getBlockX() <= block.getX() ||
                 locs[0].getBlockX() <= block.getX() && locs[1].getBlockX() >= block.getX()) &&
                 (locs[0].getBlockZ() >= block.getZ() && locs[1].getBlockZ() <= block.getZ() ||
                 locs[0].getBlockZ() <= block.getZ() && locs[1].getBlockZ() >= block.getZ())) {
                return true;
            }
        }
        return false;
    }

    public static int containedW(String current, String region, World world) {
        // Region points
        Location[] locs = FileRead.getCoordinates(region, world);

        // Regin calc
        int xR = Math.abs(locs[0].getBlockX() - locs[1].getBlockX());
        int zR = Math.abs(locs[0].getBlockZ() - locs[1].getBlockZ());
        int areaR = xR * zR;

        // Current points
        Location[] locsC = FileRead.getCoordinates(current, world);

        // Current calc
        int xC = Math.abs(locsC[0].getBlockX() - locsC[1].getBlockX());
        int zC = Math.abs(locsC[0].getBlockZ() - locsC[1].getBlockZ());
        int areaC = xC * zC;

        return areaR - areaC;
    }

    public static boolean checkRegionAtt(Location loc, Player player, String attribute, World world) {
        String name = "";
        if (player == null) {
            name = "null" + loc.getBlockX() + loc.getBlockY();
        } else {
            name = player.getUniqueId().toString();
        }


        Set<String> regions = getRegions(world);
        if (regions != null) {
            // The tits of block events
            for (String region : regions) {
                if (checkIfInRegion(loc.getBlock(), world, region)) {
                    pToInner.put(name, region);
                    for (String region2Check: regions) {
                        if (checkIfInRegion(loc.getBlock(), world, region2Check)) {
                            if (containedW(pToInner.get(name), region2Check, world) <= 0) {
                                pToInner.put(name, region2Check);
                            }
                        }
                    }
                    if (containedW(pToInner.get(name), region, world) <= 0) {
                        pToInner.put(name, region);
                        String attributeValue = FileRead.getAttributeValue(region, world, attribute);
                        if (attributeValue != null && attributeValue.equalsIgnoreCase("false")) {
                            if (player == null || !player.hasPermission("WEG." + region + "." + attribute)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String getRegion(Location loc)
    {
        String name = WorldCoordinate.toString(loc.getBlock());
        World world = loc.getWorld();
        Set<String> regions = getRegions(world);
        if (regions != null)
        {
            for (String region : regions)
            {
                if (checkIfInRegion(loc.getBlock(), world, region))
                {
                    pToInner.put(name, region);
                    for (String region2Check: regions)
                    {
                        if (checkIfInRegion(loc.getBlock(), world, region2Check))
                        {
                            if (containedW(pToInner.get(name), region2Check, world) <= 0)
                            {
                                pToInner.put(name, region2Check);
                            }
                        }
                    }
                    if (containedW(pToInner.get(name), region, world) <= 0)
                    {
                        pToInner.put(name, region);
                        return region;
                    }
                }
            }
        }
        return "";
    }
}
