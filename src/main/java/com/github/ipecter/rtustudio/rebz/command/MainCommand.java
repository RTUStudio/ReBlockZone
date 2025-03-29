package com.github.ipecter.rtustudio.rebz.command;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import com.github.ipecter.rtustudio.rebz.regen.ReSchedule;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;
import kr.rtuserver.framework.bukkit.api.registry.CustomBlocks;
import org.bukkit.Location;

public class MainCommand extends RSCommand<ReBlockZone> {

    public MainCommand(ReBlockZone plugin) {
        super(plugin, "rebz");
    }

    @Override
    protected void reload(RSCommandData data) {
        plugin.getTaskMap().entrySet().removeIf(entry -> {
            Location location = entry.getKey();
            ReSchedule schedule = entry.getValue();
            if (location.getWorld() != null) CustomBlocks.place(location, schedule.getMaterial());
            return true;
        });
        plugin.initConfig();
    }

}
