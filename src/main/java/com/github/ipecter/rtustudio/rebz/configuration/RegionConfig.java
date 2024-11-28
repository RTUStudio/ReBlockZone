package com.github.ipecter.rtustudio.rebz.configuration;

import com.github.ipecter.rtustudio.rebz.ReBlockZone;
import com.github.ipecter.rtustudio.rebz.regen.ReMaterial;
import com.github.ipecter.rtustudio.rebz.regen.ReRegion;
import kr.rtuserver.framework.bukkit.api.config.RSConfiguration;
import kr.rtuserver.framework.bukkit.api.utility.compatible.BlockCompat;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RegionConfig extends RSConfiguration {

    private final ReBlockZone plugin;

    public RegionConfig(ReBlockZone plugin) {
        super(plugin, "Region.yml", null);
        this.plugin = plugin;
        setup(this);
    }

    private void init() {
        plugin.getRegenMap().clear();
        for (String key : getConfig().getKeys(false)) {
            int tick = getInt(key + ".tick", 20);
            String defaultBlock = getString(key + ".block.default", "minecraft:barrier");
            List<ReMaterial> defaultReplace = new ArrayList<>();
            for (String replace : getStringList(key + ".block.replace", List.of())) {
                String[] split = replace.split(":");
                if (split.length < 2) continue;
                String materialStr = replace.substring(0, replace.lastIndexOf(':'));
                int rarity = Integer.parseInt(split[split.length - 1]);
                if (BlockCompat.from(materialStr) != null) {
                    defaultReplace.add(new ReMaterial(materialStr, rarity));
                } else getPlugin().console("<red>" + materialStr + " 타입은 잘못된 블럭 타입입니다");
            }
            plugin.getRegenMap().put(key, new ReRegion(key, tick, defaultBlock, defaultReplace));
        }
    }
}
