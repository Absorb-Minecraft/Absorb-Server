package org.absorb.world.block.property.properties;

import org.absorb.world.block.property.BlockProperties;
import org.jetbrains.annotations.NotNull;

public class RedstoneConnectedSouthProperty extends RedstoneConnectedProperty {
    @Override
    public @NotNull String propertyName() {
        return "south";
    }

    @Override
    public RedstoneConnectedProperty opposite() {
        return BlockProperties.CONNECTED_NORTH_REDSTONE;
    }
}
