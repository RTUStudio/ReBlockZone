package com.github.ipecter.rtustudio.rebz.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;

import java.util.Set;

public class RegionUtil {

    private static final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

    public static Set<ProtectedRegion> getRegions(Location location) {
        RegionQuery regionQuery = container.createQuery();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
        ApplicableRegionSet applicableRegions = regionQuery.getApplicableRegions(loc);
        return applicableRegions.size() > 0 ? applicableRegions.getRegions() : Set.of();
    }

    public static ProtectedRegion getRegion(Location location) {
        Set<ProtectedRegion> regions = getRegions(location);
        ProtectedRegion result = null;
        for (ProtectedRegion region : regions) {
            int priority = result != null ? result.getPriority() : Integer.MAX_VALUE;
            if (region.getPriority() < priority) {
                result = region;
            }
        }
        return result;
    }

    public static String getRegionName(Location location) {
        ProtectedRegion region = getRegion(location);
        return region == null ? "" : region.getId();
    }

}
