package com.github.ipecter.rtustudio.rebz;

import com.github.ipecter.rtustudio.rebz.regen.ReSchedule;
import kr.rtuserver.framework.bukkit.api.utility.compatible.BlockCompat;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class RegenRunnable implements Runnable {

    private final ReBlockZone plugin = ReBlockZone.getInstance();

    @Override
    public void run() {
        List<Location> toRemove = new ArrayList<>();
        for (Location location : plugin.getTaskMap().keySet()) {
            ReSchedule schedule = plugin.getTaskMap().get(location);
            if (schedule.getTime() <= 0) {
                World world = location.getWorld();
                if (world != null) toRemove.add(location);
            } else schedule.setTime(schedule.getTime() - 1);
        }
        for (Location location : toRemove) {
            plugin.getTaskMap().remove(location);
            plugin.removeLocation(location);
        }
    }

}
