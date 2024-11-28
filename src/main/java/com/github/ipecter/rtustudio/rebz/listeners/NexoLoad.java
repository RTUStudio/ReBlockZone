package com.github.ipecter.rtustudio.rebz.listeners;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import com.nexomc.nexo.api.events.NexoItemsLoadedEvent;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;

public class NexoLoad extends RSListener {

    private final ReBlockZone plugin;

    public NexoLoad(ReBlockZone plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onNexoLoad(NexoItemsLoadedEvent e) {
        plugin.initConfig();
        plugin.fixSchedule();
    }
}
