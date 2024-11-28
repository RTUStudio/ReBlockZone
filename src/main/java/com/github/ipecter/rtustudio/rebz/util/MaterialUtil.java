package com.github.ipecter.rtustudio.rebz.util;

import com.github.ipecter.rtustudio.rebz.regen.ReMaterial;

import java.util.ArrayList;
import java.util.List;

public class MaterialUtil {

    public static String getRandomBlockData(List<ReMaterial> materials) {
        List<String> select = new ArrayList<>();
        for (ReMaterial material : materials) {
            for (int i = 0; i < material.rarity(); i++) {
                select.add(material.material());
            }
        }
        return select.get((int) (Math.random() * select.size()));
    }
}
