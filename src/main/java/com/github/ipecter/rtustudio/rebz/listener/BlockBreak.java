package com.github.ipecter.rtustudio.rebz.listener;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import com.github.ipecter.rtustudio.rebz.regen.ReMaterial;
import com.github.ipecter.rtustudio.rebz.regen.ReRegion;
import com.github.ipecter.rtustudio.rebz.regen.ReSchedule;
import com.github.ipecter.rtustudio.rebz.util.MaterialUtil;
import com.github.ipecter.rtustudio.rebz.util.RegionUtil;
import kr.rtuserver.framework.bukkit.api.listener.RSListener;
import kr.rtuserver.framework.bukkit.api.registry.CustomBlocks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends RSListener<ReBlockZone> {

    public BlockBreak(ReBlockZone plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        Block block = e.getBlock();
        Location location = block.getLocation();
        String regionName = RegionUtil.getRegionName(location);
        for (ReRegion region : getPlugin().getRegenMap().values()) {
            for (String v : region.regions()) {
                if (v.equals(regionName)) {
                    String blockName = CustomBlocks.to(block);
                    for (ReMaterial reMaterial : region.replaceBlock()) {
                        if (reMaterial.material().equalsIgnoreCase(blockName)) {
                            String material = MaterialUtil.getRandomBlockData(region.replaceBlock());
                            int time = region.delay();
                            if (region.defaultBlock().equalsIgnoreCase(blockName)) return;
                            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                                if (!CustomBlocks.place(location, region.defaultBlock()))
                                    getPlugin().console("<red>블럭 데이터 오류: " + region.defaultBlock() + "</red>");
                                getPlugin().getTaskMap().put(location, new ReSchedule(region, material, time));
                                getPlugin().addLocation(location, material);
                            }, 1);
                            return;
                        }
                    }
                    if (region.protect()) e.setCancelled(true);
                }
            }
        }
    }
}
