package com.github.ipecter.rtustudio.rebz;

import com.github.ipecter.rtustudio.rebz.regen.ReRegion;
import com.github.ipecter.rtustudio.rebz.regen.ReSchedule;
import kr.rtuserver.framework.bukkit.api.registry.CustomBlocks;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class RegenRunnable implements Runnable {

    private final ReBlockZone plugin = ReBlockZone.getInstance();

    @Override
    public void run() {
        Set<String> done = new HashSet<>();
        List<Location> toRemove = new ArrayList<>();

        plugin.getTaskMap().entrySet().removeIf(entry -> {
            Location location = entry.getKey();
            ReSchedule schedule = entry.getValue();

            if (schedule.getTime() <= 0) {
                if (location.getWorld() == null) return false;
                CustomBlocks.place(location, schedule.getMaterial());
                done.add(schedule.getRegion().name());
                toRemove.add(location);
                return true;
            }

            schedule.setTime(schedule.getTime() - 1);
            return false;
        });

        plugin.getTaskMap().entrySet().removeIf(entry -> {
            ReSchedule schedule = entry.getValue();
            if (!done.contains(schedule.getRegion().name())) return false;

            if (!schedule.getRegion().global()) return false;

            Location location = entry.getKey();
            if (location.getWorld() == null) return false;

            CustomBlocks.place(location, schedule.getMaterial());
            return true;
        });

        toRemove.forEach(plugin::removeLocation);
    }


}
