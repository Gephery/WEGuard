package com.gmail.gephery.guard.controller;

import com.gmail.gephery.guard.buffers.RegionBuffer;
import com.gmail.gephery.guard.file.FileRead;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxgr on 7/3/2016.
 */
public class EventPlayerMoveEvent implements Listener {

    private JavaPlugin plugin;
    private Map<String, Set<String>> inRegion;

    public EventPlayerMoveEvent(JavaPlugin plugin, Map<String, Set<String>> inRegion) {
        this.plugin = plugin;
        this.inRegion = inRegion;
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        Location loc = player.getLocation();

        Set<String> regions = RegionBuffer.getRegions(world);
        if (regions != null) {
            for (String region : regions) {
                if (RegionBuffer.checkIfInRegion(loc.getBlock(), world, region)) {
                    if (inRegion.get(region) == null) {
                        inRegion.put(region, new HashSet<String>());
                    }
                    String attributeValue = FileRead.getAttributeValue(region, world, "title");
                    if (attributeValue != null &&
                            !inRegion.get(region).contains(player.getName())) {
                        inRegion.get(region).add(player.getName());
                        player.sendMessage("Entering " + region  + "!");
                    }
                } else if (inRegion.get(region) != null && inRegion.get(region).contains(player.getName())) {
                    inRegion.get(region).remove(player.getName());
                    player.sendMessage("Leaving " + region  + ", bye!");
                }
            }
        }
    }
}
