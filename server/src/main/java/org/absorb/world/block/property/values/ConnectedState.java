package org.absorb.world.block.property.values;

public enum ConnectedState {

    NONE,
    SIDE,
    UP;

    public String propertyValue() {
        return name().toLowerCase();
    }
}
