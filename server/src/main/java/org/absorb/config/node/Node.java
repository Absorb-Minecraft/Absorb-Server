package org.absorb.config.node;

import org.absorb.config.Config;
import org.absorb.utils.lamda.ThrowableTriConsumer;
import org.absorb.utils.lamda.consumer.ThrowableConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Node<T> {

    private final Object[] nodePath;
    private final T defaultValue;
    private final BiFunction<Config, Node<T>, T> getter;
    private final @NotNull ThrowableConsumer<T, IllegalArgumentException> verify;
    private final @Nullable String comment;
    private final ThrowableTriConsumer<Config, Node<T>, T, SerializationException> setter;
    private @Nullable T cachedValue;

    Node(@NotNull NodeBuilder<T> builder) {
        this.nodePath = Objects.requireNonNull(builder.nodePath(), "NodePath is null");
        if (this.nodePath.length == 0) {
            throw new IllegalArgumentException("NodePath is empty");
        }
        this.defaultValue = Objects.requireNonNull(builder.defaultValue(), "DefaultValue is null");
        this.getter = Objects.requireNonNull(builder.getter(), "Getter is null");
        this.setter = Objects.requireNonNull(builder.setter(), "Setter is null");
        this.comment = builder.comment();
        this.verify = Objects.requireNonNullElse(builder.verify(), (t) -> {
        });
    }

    public static <Value> NodeBuilder<Value> builder() {
        return new NodeBuilder<>();
    }

    public Object[] path() {
        return this.nodePath;
    }

    public T defaultValue() {
        return this.defaultValue;
    }

    public void clearCache() {
        this.cachedValue = null;
    }

    public T value(@NotNull Config config) {
        if (this.cachedValue == null) {
            T value = this.getter.apply(config, this);
            this.verify.apply(value);
            this.cachedValue = value;
        }
        if (this.cachedValue == null) {
            throw new IllegalStateException("Cannot gain '" + Arrays.stream(path()).map(Object::toString).collect(Collectors.joining("->")) + "' from config '" + config.file().getPath() + "'");
        }
        return this.cachedValue;
    }

    public void setValue(@NotNull Config config, T value) throws SerializationException {
        this.cachedValue = value;
        this.setter.accept(config, this, value);
        if (this.comment != null) {
            config.rootNode().node(this.nodePath).commentIfAbsent(this.comment);
        }
    }
}
