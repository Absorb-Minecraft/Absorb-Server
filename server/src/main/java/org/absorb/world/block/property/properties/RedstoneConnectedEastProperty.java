package org.absorb.world.block.property.properties;

import org.absorb.world.block.property.BlockProperties;
import org.jetbrains.annotations.NotNull;

public class RedstoneConnectedEastProperty extends RedstoneConnectedProperty {

    @Override
    public @NotNull String propertyName() {
        return "east";
    }

    @Override
    public RedstoneConnectedProperty opposite() {
        return BlockProperties.CONNECTED_WEST_REDSTONE;
    }
}
