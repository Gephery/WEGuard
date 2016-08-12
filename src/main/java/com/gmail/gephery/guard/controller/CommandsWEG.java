package com.gmail.gephery.guard.controller;

import static com.gmail.gephery.guard.controller.WEGText.*;

import com.gmail.gephery.guard.buffers.RegionBuffer;
import com.gmail.gephery.guard.file.FileWrite;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxgr on 7/15/2016.
 */
public class CommandsWEG implements CommandExecutor {

    public static final int SELECT_DISTANCE = 100;

    private static Map<Player, Location[]> pToLoc = new HashMap<Player, Location[]>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            World world = player.getWorld();
            if (args.length >= 1) {
                if (args[0].equals(CREATE_CMD) && player.hasPermission(CREATE_CMD_PERM)) {
                    if (args.length >= 2) {
                        if (pToLoc.get(player) != null && pToLoc.get(player)[0] != null &&
                                pToLoc.get(player)[1] != null) {
                            String region = args[1];
                            FileWrite.setCoordinates(region, world, pToLoc.get(player));
                            RegionBuffer.reloadRegions(world);
                            player.sendMessage(args[1] + " has been created, dude.");
                        }
                    } else {
                        player.sendMessage(CREATE_CMD_USAGE);
                    }
                    return true;
                } else if (args[0].equals(SELECT_CMD) && player.hasPermission(SELECT_CMD_PERM)) {
                    if (args.length >= 2) {
                        Set<Material> transparentB = null;
                        Location loc = player.getTargetBlock(transparentB, SELECT_DISTANCE)
                                        .getLocation();
                        Location[] locs = pToLoc.get(player);
                        if (locs == null) {
                            pToLoc.put(player, new Location[2]);
                            locs = pToLoc.get(player);
                        }

                        if (args[1].equals(SELECT_P1)) {
                            locs[0] = loc;
                            player.sendMessage(SELECT_P1_MESSAGE);
                            return true;
                        } else if (args[1].equals(SELECT_P2)) {
                            locs[1] = loc;
                            player.sendMessage(SELECT_P2_MESSAGE);
                            return true;
                        }
                    } else {
                        player.sendMessage(SELECT_CMD_USAGE);
                    }
                    return true;
                } else if (args[0].equals(EDIT_CMD) && player.hasPermission(EDIT_CMD_PERM)) {
                    if (args.length >= 4) {
                        StringBuilder attributeValue = new StringBuilder();
                        for (int i = 3; i < args.length; i++) {
                            attributeValue.append(" " + args[i]);
                        }
                        String region = args[1];
                        String attribute = args[2];

                        FileWrite.setAttributeValue(region, world, attribute, attributeValue.toString().trim());

                        sender.sendMessage(args[2] + " has been added to region " + args[1] + " in " +
                                world.getName());
                    } else {
                        player.sendMessage(EDIT_CMD_USAGE);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
