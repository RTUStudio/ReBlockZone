package com.github.ipecter.rtustudio.rebz.listeners;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import io.th0rgal.oraxen.api.events.OraxenItemsLoadedEvent;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;

public class OraxenLoad extends RSListener {

    private final ReBlockZone plugin;

    public OraxenLoad(ReBlockZone plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onOraxenLoad(OraxenItemsLoadedEvent e) {
        plugin.initConfig();
        plugin.fixSchedule();
    }
}
