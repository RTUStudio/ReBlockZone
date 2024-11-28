package com.github.ipecter.rtustudio.rebz.regen;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReRegion {

    private final String name;
    private final int tick;
    private final String defaultBlock;
    private final List<ReMaterial> replaceBlock;

}
