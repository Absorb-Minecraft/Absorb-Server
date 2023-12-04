package org.absorb.config.serializer.java;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDSerializer implements TypeSerializer<UUID> {
    @Override
    public UUID deserialize(Type type, ConfigurationNode node) throws SerializationException {
        String uuid = node.getString();
        if (uuid == null) {
            return null;
        }
        try {
            return UUID.fromString(uuid);
        } catch (NumberFormatException e) {
            throw new SerializationException(node, UUID.class, e);
        }
    }

    @Override
    public void serialize(Type type, @Nullable UUID obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.raw(null);
            return;
        }
        node.set(obj.toString());
    }
}
