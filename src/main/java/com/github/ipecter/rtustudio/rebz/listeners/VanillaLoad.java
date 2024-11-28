package com.github.ipecter.rtustudio.rebz.listeners;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerLoadEvent;

public class VanillaLoad extends RSListener {

    private final ReBlockZone plugin;

    public VanillaLoad(ReBlockZone plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerDone(ServerLoadEvent e) {
        plugin.initConfig();
        plugin.fixSchedule();
    }
}
