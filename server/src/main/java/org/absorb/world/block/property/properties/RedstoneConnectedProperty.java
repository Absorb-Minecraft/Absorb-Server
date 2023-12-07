package org.absorb.world.block.property.properties;

import org.absorb.world.block.property.BlockEnumProperty;
import org.absorb.world.block.property.values.ConnectedState;

public abstract class RedstoneConnectedProperty extends BlockEnumProperty.Writable<ConnectedState> {
    public RedstoneConnectedProperty() {
        super(ConnectedState.class);
    }

    public abstract RedstoneConnectedProperty opposite();
}
