package org.absorb.world.block.property.properties;

import org.absorb.world.block.property.BlockProperties;
import org.jetbrains.annotations.NotNull;

public class RedstoneConnectedNorthProperty extends RedstoneConnectedProperty {

    @Override
    public @NotNull String propertyName() {
        return "north";
    }

    @Override
    public RedstoneConnectedProperty opposite() {
        return BlockProperties.CONNECTED_SOUTH_REDSTONE;
    }
}
