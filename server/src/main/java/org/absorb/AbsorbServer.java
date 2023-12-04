package org.absorb;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.world.DimensionType;
import org.absorb.config.ConfigManager;
import org.absorb.platform.AbsorbPlatform;
import org.absorb.utils.Singleton;
import org.absorb.world.WorldManagerExtensions;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

public class AbsorbServer {

    public static final String ABSORB = "absorb";
    private static final @NotNull Singleton<ConfigManager> config = new Singleton<>(ConfigManager::new);
    private static @NotNull AbsorbPlatform platform;

    public static ConfigManager config() {
        return config.get();
    }

    public static @NotNull AbsorbPlatform platform() {
        return platform;
    }

    public static void launch(@NotNull AbsorbPlatform plat, String... args) throws IOException {
        platform = plat;
        MinecraftServer server = MinecraftServer.init();

        System.out.println("loading configurations");
        ConfigManager configManager = config();


        System.out.println("loading main world");
        InstanceContainer world;
        try {
            world = WorldManagerExtensions.loadWorldSynced(configManager.world().defaultWorld().value());
        } catch (IOException e) {
            world = new InstanceContainer(UUID.randomUUID(), DimensionType.OVERWORLD);
        }
        MinecraftServer.getInstanceManager().registerInstance(world);
        final var finalWorld = world;
        MinecraftServer.getGlobalEventHandler().addListener(PlayerLoginEvent.class, event -> {
            event.setSpawningInstance(finalWorld);
        });

        server.start(configManager.connection().getIp(), configManager.connection().getPort());
        System.out.println("Server started");
    }
}
