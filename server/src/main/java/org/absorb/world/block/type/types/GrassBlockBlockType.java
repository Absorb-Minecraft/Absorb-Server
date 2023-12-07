package org.absorb.world.block.type.types;

import net.minestom.server.instance.block.Block;
import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.type.AbstractBlockType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class GrassBlockBlockType extends AbstractBlockType {

    public GrassBlockBlockType() {
        super(Block.GRASS_BLOCK);
    }

    @Override
    public @NotNull Stream<BlockProperty<?>> properties() {
        return Stream.empty();
    }
}
