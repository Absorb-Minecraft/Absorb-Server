package org.absorb.world.block.property;

import org.jetbrains.annotations.NotNull;

public abstract class BlockBooleanProperty implements BlockProperty<Boolean> {

    @Override
    public @NotNull Boolean parser(@NotNull String value) {
        return switch (value.toLowerCase()) {
            case "true" -> true;
            case "false" -> false;
            default -> throw new RuntimeException("Can only accept true/false. Got '" + value.toLowerCase() + "'");
        };
    }
}
