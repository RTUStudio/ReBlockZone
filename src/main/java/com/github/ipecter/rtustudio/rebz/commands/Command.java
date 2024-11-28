package com.github.ipecter.rtustudio.rebz.commands;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import com.github.ipecter.rtustudio.rebz.configuration.RegionConfig;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;

public class Command extends RSCommand {

    private final ReBlockZone plugin;

    public Command(ReBlockZone plugin) {
        super(plugin, "rebz", true);
        this.plugin = plugin;
    }

    @Override
    protected void reload(RSCommandData data) {
        plugin.initConfig();
    }

}
