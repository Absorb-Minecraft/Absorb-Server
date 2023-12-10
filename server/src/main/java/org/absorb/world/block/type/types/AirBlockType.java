package org.absorb.world.block.type.types;

import net.minestom.server.instance.block.Block;
import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.type.AbstractBlockType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class AirBlockType extends AbstractBlockType {
    public AirBlockType() {
        super(Block.AIR);
    }

    @Override
    public @NotNull Stream<BlockProperty<?>> properties() {
        return Stream.empty();
    }
}
