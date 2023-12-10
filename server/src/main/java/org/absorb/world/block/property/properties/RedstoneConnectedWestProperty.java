package org.absorb.world.block.property.properties;

import org.absorb.world.block.property.BlockProperties;
import org.jetbrains.annotations.NotNull;

public class RedstoneConnectedWestProperty extends RedstoneConnectedProperty {

    @Override
    public @NotNull String propertyName() {
        return "west";
    }

    @Override
    public RedstoneConnectedProperty opposite() {
        return BlockProperties.CONNECTED_EAST_REDSTONE;
    }
}
