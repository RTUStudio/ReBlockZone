package com.github.ipecter.rtustudio.rebz.listeners;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import com.github.ipecter.rtustudio.rebz.regen.ReMaterial;
import com.github.ipecter.rtustudio.rebz.regen.ReRegion;
import com.github.ipecter.rtustudio.rebz.regen.ReSchedule;
import com.github.ipecter.rtustudio.rebz.util.MaterialUtil;
import com.github.ipecter.rtustudio.rebz.util.RegionUtil;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import kr.rtuserver.framework.bukkit.api.utility.compatible.BlockCompat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends RSListener {

    private final ReBlockZone plugin;

    public BlockBreak(ReBlockZone plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        Block block = e.getBlock();
        Location location = block.getLocation();
        String regionName = RegionUtil.getRegionName(location);
        if (plugin.getRegenMap().containsKey(regionName)) {
            ReRegion regenRegion = plugin.getRegenMap().get(regionName);
            String material = MaterialUtil.getRandomBlockData(regenRegion.getReplaceBlock());
            int time = regenRegion.getTick();
            if (BlockCompat.to(block).equalsIgnoreCase(regenRegion.getDefaultBlock())) return;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (!BlockCompat.place(location, regenRegion.getDefaultBlock()))
                    plugin.console("<red>블럭 데이터 오류: " + regenRegion.getDefaultBlock() + "</red>");
                plugin.getTaskMap().put(location, new ReSchedule(material, time));
                plugin.addLocation(location, material);
            }, 1);
        }
    }
}
