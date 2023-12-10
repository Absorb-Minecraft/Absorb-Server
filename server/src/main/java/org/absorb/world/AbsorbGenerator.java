package org.absorb.world;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.generator.GenerationUnit;
import net.minestom.server.instance.generator.Generator;
import org.jetbrains.annotations.NotNull;

public class AbsorbGenerator implements Generator {
    @Override
    public void generate(@NotNull GenerationUnit unit) {
        unit.modifier().fillHeight(0, 2, Block.BEDROCK);
        unit.modifier().fillHeight(2, 4, Block.GRASS_BLOCK);
    }
}
