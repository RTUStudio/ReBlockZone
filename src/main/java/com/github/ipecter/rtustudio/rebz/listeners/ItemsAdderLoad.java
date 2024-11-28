package com.github.ipecter.rtustudio.rebz.listeners;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import org.bukkit.event.EventHandler;

public class ItemsAdderLoad extends RSListener {

    private final ReBlockZone plugin;

    public ItemsAdderLoad(ReBlockZone plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemsAdderLoad(ItemsAdderLoadDataEvent e) {
        if (e.getCause() == ItemsAdderLoadDataEvent.Cause.FIRST_LOAD) {
            plugin.initConfig();
            plugin.fixSchedule();
        }
    }

}
