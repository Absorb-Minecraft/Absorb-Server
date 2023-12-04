package org.absorb.config.node;

import net.minestom.server.utils.NamespaceID;
import org.absorb.config.Config;
import org.absorb.utils.lamda.ThrowableTriConsumer;
import org.absorb.utils.lamda.consumer.ThrowableConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.function.BiFunction;

public class NodeBuilder<T> {

    private Object[] nodePath;
    private T defaultValue;
    private BiFunction<Config, Node<T>, T> getter;
    private ThrowableTriConsumer<Config, Node<T>, T, SerializationException> setter;
    private String comment;
    private ThrowableConsumer<T, IllegalArgumentException> verify;

    public @Nullable ThrowableConsumer<T, IllegalArgumentException> verify() {
        return this.verify;
    }

    public NodeBuilder<T> setVerify(@Nullable ThrowableConsumer<T, IllegalArgumentException> verify) {
        this.verify = verify;
        return this;
    }

    public @Nullable String comment() {
        return this.comment;
    }

    public NodeBuilder<T> setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Object[] nodePath() {
        return nodePath;
    }

    public NodeBuilder<T> setNodePath(@NotNull Object... nodePath) {
        this.nodePath = nodePath;
        return this;
    }

    public T defaultValue() {
        return defaultValue;
    }

    public NodeBuilder<T> setDefaultValue(@NotNull T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public BiFunction<Config, Node<T>, T> getter() {
        return getter;
    }

    public NodeBuilder<T> setGetter(@NotNull BiFunction<Config, Node<T>, T> getter) {
        this.getter = getter;
        return this;
    }

    public ThrowableTriConsumer<Config, Node<T>, T, SerializationException> setter() {
        return setter;
    }

    public NodeBuilder<T> setSetter(@NotNull ThrowableTriConsumer<Config, Node<T>, T, SerializationException> setter) {
        this.setter = setter;
        return this;
    }

    public Node<T> build() {
        return new Node<>(this);
    }

    public NodeBuilder<NamespaceID> setResourceKey() {
        NodeBuilder<NamespaceID> nodeBuilder = (NodeBuilder<NamespaceID>) this;
        nodeBuilder.getter = (config, objectNode) -> {
            String v = config.rootNode().node(objectNode.path()).getString();
            if (v == null) {
                return null;
            }
            return NamespaceID.from(v);
        };
        nodeBuilder.setter = (config, resourceKeyNode, resourceKey) -> config.rootNode().node(resourceKeyNode.path()).set(resourceKey == null ? null : resourceKey.asString());
        return nodeBuilder;
    }

    public NodeBuilder<Boolean> setBoolean() {
        NodeBuilder<Boolean> nodeBuilder = (NodeBuilder<Boolean>) this;
        nodeBuilder.setter = (config, tNode, t) -> config.rootNode().node(tNode.path()).set(t);
        nodeBuilder.getter = (config, tNode) -> {
            var node = config.rootNode().node(tNode.path());
            if (node.isNull()) {
                return null;
            }
            return node.getBoolean();
        };
        return nodeBuilder;
    }

    public NodeBuilder<String> setString() {
        NodeBuilder<String> nodeBuilder = (NodeBuilder<String>) this;
        nodeBuilder.setter = (config, tNode, t) -> config.rootNode().node(tNode.path()).set(t);
        nodeBuilder.getter = (config, tNode) -> config.rootNode().node(tNode.path()).getString();
        return nodeBuilder;
    }

    public NodeBuilder<Integer> setInteger() {
        NodeBuilder<Integer> nodeBuilder = (NodeBuilder<Integer>) this;
        nodeBuilder.setter = (config, tNode, t) -> config.rootNode().node(tNode.path()).set(t);
        nodeBuilder.getter = (config, tNode) -> {
            var node = config.rootNode().node(tNode.path());
            if (node.isNull()) {
                return null;
            }
            return node.getInt();
        };
        return nodeBuilder;
    }
}
