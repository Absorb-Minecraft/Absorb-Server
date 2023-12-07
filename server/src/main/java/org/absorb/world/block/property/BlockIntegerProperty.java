package org.absorb.world.block.property;

import org.jetbrains.annotations.NotNull;

public abstract class BlockIntegerProperty implements BlockProperty<Integer> {

    @Override
    public @NotNull Integer parser(@NotNull String value) {
        return Integer.parseInt(value);
    }

    public static abstract class Writable extends BlockIntegerProperty implements BlockProperty.Writable<Integer> {

    }

    public static abstract class Readable extends BlockIntegerProperty implements BlockProperty.Readable<Integer> {

    }
}
