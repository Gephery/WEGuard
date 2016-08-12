package com.gmail.gephery.guard.main;

import com.gmail.gephery.teleport.buffers.FileBuffer;
import static com.gmail.gephery.guard.controller.WEGText.*;

import com.gmail.gephery.guard.buffers.RegionBuffer;
import com.gmail.gephery.guard.controller.CommandsWEG;
import com.gmail.gephery.guard.controller.EventBlockEvent;
import com.gmail.gephery.guard.controller.EventPlayerMoveEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxgr on 7/2/2016.
 */
public class WorldExplorerGuard extends JavaPlugin {

    @Override
    public void onEnable() {

        Map<String, Set<String>> inRegion = new HashMap<String, Set<String>>();
        Map<String, String> pToInner = new HashMap<String, String>();

        FileBuffer fBuffer = new FileBuffer(this, "region.yml");
        RegionBuffer rBuffer = new RegionBuffer();

        this.getServer().getPluginManager().registerEvents(new
                EventPlayerMoveEvent(this, inRegion), this);
        this.getServer().getPluginManager().registerEvents(new
                EventBlockEvent(), this);

        this.getCommand("weg").setExecutor(new CommandsWEG());

        Permission wegCreatePerm = new Permission(CREATE_CMD_PERM);
        Permission wegSelectPerm = new Permission(SELECT_CMD_PERM);
        Permission wegEditPerm = new Permission(EDIT_CMD_PERM);

        PluginManager pM = getServer().getPluginManager();
        pM.addPermission(wegCreatePerm);
        pM.addPermission(wegSelectPerm);
        pM.addPermission(wegEditPerm);
    }

    @Override
    public void onDisable() {
    }
}
