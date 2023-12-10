package org.absorb.world.block.property.properties;

import org.absorb.world.block.property.BlockBooleanProperty;
import org.jetbrains.annotations.NotNull;

public class LitProperty extends BlockBooleanProperty {
    @Override
    public @NotNull String propertyName() {
        return "lit";
    }
}
