package com.github.ipecter.rtustudio.rebz;

import com.github.ipecter.rtustudio.rebz.command.MainCommand;
import com.github.ipecter.rtustudio.rebz.configuration.RegionConfig;
import com.github.ipecter.rtustudio.rebz.listener.BlockBreak;
import com.github.ipecter.rtustudio.rebz.listener.PluginItemLoaded;
import com.github.ipecter.rtustudio.rebz.regen.ReRegion;
import com.github.ipecter.rtustudio.rebz.regen.ReSchedule;
import com.google.gson.JsonObject;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.platform.JSON;
import kr.rtuserver.framework.bukkit.api.registry.CustomBlocks;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ReBlockZone extends RSPlugin {

    @Getter
    private static ReBlockZone instance;
    @Getter
    private final Map<Location, ReSchedule> taskMap = new HashMap<>();
    @Getter
    private final Map<String, ReRegion> regenMap = new HashMap<>();
    @Getter
    private RegionConfig regionConfig;
    @Getter
    private BukkitTask regenTask;

    @Override
    public void enable() {
        instance = this;
        getConfigurations().getStorage().init("Regen");
        regenTask = Bukkit.getScheduler().runTaskTimer(this, new RegenRunnable(), 0, 1);

        registerEvent(new BlockBreak(this));
        registerEvent(new PluginItemLoaded(this));

        registerCommand(new MainCommand(this), true);
    }

    public void initConfig() {
        if (regionConfig != null) regionConfig.reload();
        else regionConfig = new RegionConfig(this);
    }


    public void addLocation(Location loc, String material) {
        JsonObject object = new JsonObject();
        String location = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
        object.addProperty("location", location);
        object.addProperty("material", material);
        getStorage().add("Regen", object).join();
    }

    public void removeLocation(Location loc) {
        String location = loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
        getStorage().set("Regen", JSON.of("location", location), null).join();
    }

    public void fixSchedule() {
        getStorage().get("Regen", JSON.of()).thenAccept((result) -> {
            if (!result.isEmpty()) console("백업을 시도합니다");
            for (JsonObject object : result) {
                String[] loc = object.get("location").getAsString().split(",");
                String materialStr = object.get("material").getAsString();
                Location location = new Location(Bukkit.getWorld(loc[0]), Integer.parseInt(loc[1]), Integer.parseInt(loc[2]), Integer.parseInt(loc[3]));
                BlockData material = CustomBlocks.from(materialStr);
                if (material == null) material = Material.AIR.createBlockData();
                location.getWorld().setBlockData(location, material);
                removeLocation(location);
            }
        });
    }

}
