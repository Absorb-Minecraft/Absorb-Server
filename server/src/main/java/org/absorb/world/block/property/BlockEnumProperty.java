package org.absorb.world.block.property;

import org.jetbrains.annotations.NotNull;

public abstract class BlockEnumProperty<E extends Enum<E>> implements BlockProperty<E> {

    private final Class<E> clazz;

    public BlockEnumProperty(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public @NotNull E parser(@NotNull String value) {
        return Enum.valueOf(this.clazz, value.toUpperCase());
    }

    public static abstract class Writable<E extends Enum<E>> extends BlockEnumProperty<E> implements BlockProperty.Writable<E> {

        public Writable(Class<E> clazz) {
            super(clazz);
        }

        @Override
        public @NotNull String parser(@NotNull E value) {
            return value.name().toLowerCase();
        }
    }

    public static abstract class Readable<E extends Enum<E>> extends BlockEnumProperty<E> implements BlockProperty.Readable<E> {

        public Readable(Class<E> clazz) {
            super(clazz);
        }
    }
}
