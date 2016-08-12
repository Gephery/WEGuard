package com.gmail.gephery.guard.controller;

import com.gmail.gephery.guard.buffers.RegionBuffer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maxgr on 7/3/2016.
 */
public class EventBlockEvent implements Listener {

    public EventBlockEvent() {/* Does nothing */}

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        // Block coords
        Block block = event.getBlock();
        boolean cancel = RegionBuffer.checkRegionAtt(block.getLocation(), player, "break",
                            player.getWorld());
        if (cancel) {
            event.setCancelled(cancel);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Player player = null;
        Entity entity = event.getEntity();

        // Block coords
        Location loc = entity.getLocation();
        boolean cancel = RegionBuffer.checkRegionAtt(loc, player, "tnt", entity.getWorld());
        if (cancel) {
            event.setCancelled(cancel);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        // Block coords
        Block block = event.getBlock();
        boolean cancel = RegionBuffer.checkRegionAtt(block.getLocation(), player, "place",
                            player.getWorld());
        if (cancel) {
            event.setCancelled(cancel);
        }
    }

    @EventHandler
    public void onBlockBurnEvent(BlockBurnEvent event) {
        Player player = null;
        Block block = event.getBlock();

        // Block coords
        boolean cancel = RegionBuffer.checkRegionAtt(block.getLocation(), player, "burn",
                            block.getWorld());
        if (cancel) {
            event.setCancelled(cancel);
        }
    }

    @EventHandler
    public void onEntityDamagebyEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            Entity entity = event.getEntity();
            Block block = entity.getLocation().getBlock();

            if (entity instanceof Player) {
                boolean cancel = RegionBuffer.checkRegionAtt(block.getLocation(), player, "pvp",
                                    player.getWorld());
                if (cancel) {
                    event.setCancelled(cancel);
                }
            } else {
                boolean cancel = RegionBuffer.checkRegionAtt(block.getLocation(), player, "pve",
                                    player.getWorld());
                if (cancel) {
                    event.setCancelled(cancel);
                }
            }
        }
    }

    @EventHandler
    public void onCreateSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        Block block = entity.getLocation().getBlock();

        // Block coords
        boolean cancel = RegionBuffer.checkRegionAtt(block.getLocation(), null, "creature",
                            entity.getWorld());
        if (cancel) {
            event.setCancelled(cancel);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        Set<EntityDamageEvent.DamageCause> invalidCauses = new HashSet<EntityDamageEvent.DamageCause>();

        // Invalid ways to get hurt in that region.
        invalidCauses.add(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
        invalidCauses.add(EntityDamageEvent.DamageCause.MAGIC);
        invalidCauses.add(EntityDamageEvent.DamageCause.MELTING);
        invalidCauses.add(EntityDamageEvent.DamageCause.POISON);
        invalidCauses.add(EntityDamageEvent.DamageCause.PROJECTILE);
        invalidCauses.add(EntityDamageEvent.DamageCause.THORNS);

        Entity entity = event.getEntity();

        Block block = entity.getLocation().getBlock();

        if (invalidCauses.contains(cause)) {
            boolean cancel = RegionBuffer.checkRegionAtt(block.getLocation(), null, "pv",
                                entity.getWorld());
            if (cancel) {
                event.setCancelled(cancel);
            }
        }
    }
}
