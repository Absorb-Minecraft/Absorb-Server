package org.absorb.world.block.property.properties;

import net.minestom.server.instance.block.Block;
import org.absorb.world.block.property.BlockIntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class BlockPowerProperty extends BlockIntegerProperty.Writable {
    @Override
    public @NotNull String propertyName() {
        return "power";
    }

    @Override
    public Block withValue(@NotNull Block block, @NotNull @Range(from = 0, to = 15) Integer value) {
        return super.withValue(block, value);
    }

    @Override
    @Range(from = 0, to = 15)
    public @NotNull Integer value(@NotNull Block block) {
        return super.value(block);
    }
}
