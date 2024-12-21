package com.github.ipecter.rtustudio.rebz.commands;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;

public class Command extends RSCommand<ReBlockZone> {

    public Command(ReBlockZone plugin) {
        super(plugin, "rebz", true);
    }

    @Override
    protected void reload(RSCommandData data) {
        getPlugin().initConfig();
    }

}
