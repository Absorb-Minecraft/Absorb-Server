package org.absorb.utils;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class Singleton<T> implements Supplier<T> {

    private final @NotNull Supplier<T> getter;
    private T cache;

    public Singleton(@NotNull Supplier<T> getter) {
        this.getter = getter;
    }

    @Override
    public @NotNull T get() {
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    cache = getter.get();
                }
            }
        }
        return cache;
    }
}
