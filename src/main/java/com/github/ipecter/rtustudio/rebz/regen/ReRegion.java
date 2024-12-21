package com.github.ipecter.rtustudio.rebz.regen;

import java.util.List;

public record ReRegion(String name, String region, int delay, String defaultBlock, List<ReMaterial> replaceBlock, boolean protect) {

}
