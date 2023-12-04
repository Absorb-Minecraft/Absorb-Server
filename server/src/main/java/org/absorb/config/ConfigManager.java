package org.absorb.config;

import org.absorb.config.connection.ConnectionConfig;
import org.absorb.config.gameplay.GameplayConfig;
import org.absorb.config.serializer.java.UUIDSerializer;
import org.absorb.config.world.WorldConfig;
import org.absorb.utils.Singleton;
import org.absorb.utils.TypeSerializerCollectionBuilder;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.util.UUID;

public class ConfigManager {

    private final Singleton<TypeSerializerCollection> serializers = new Singleton<>(() -> new TypeSerializerCollectionBuilder()
            .register(UUID.class, new UUIDSerializer())
            .build());

    private final Singleton<ConnectionConfig> connection = new Singleton<>(() -> {
        var config = new ConnectionConfig();
        config.updateMissing();
        return config;
    });

    private final Singleton<GameplayConfig> gameplay = new Singleton<>(() -> {
        var config = new GameplayConfig();
        config.updateMissing();
        return config;
    });

    private final Singleton<WorldConfig> world = new Singleton<>(() -> {
        var config = new WorldConfig();
        config.updateMissing();
        return config;
    });

    public WorldConfig world() {
        return this.world.get();
    }

    public GameplayConfig gameplay() {
        return this.gameplay.get();
    }

    public ConnectionConfig connection() {
        return this.connection.get();
    }

    public TypeSerializerCollection serializers() {
        return this.serializers.get();
    }


}
