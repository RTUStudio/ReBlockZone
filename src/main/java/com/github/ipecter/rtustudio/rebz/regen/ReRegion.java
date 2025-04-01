package com.github.ipecter.rtustudio.rebz.regen;

import java.util.List;

public record ReRegion(String name, List<String> regions, boolean global, int minDelay, int maxDelay,
                       String defaultBlock, List<ReMaterial> replaceBlock, boolean protect) {


    public int delay() {
        return (int) (Math.random() * (maxDelay - minDelay + 1)) + minDelay;
    }

}
