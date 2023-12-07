package org.absorb;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import org.absorb.config.ConfigManager;
import org.absorb.platform.AbsorbPlatform;
import org.absorb.utils.Singleton;
import org.absorb.world.AbsorbGenerator;
import org.absorb.world.WorldManagerExtensions;
import org.absorb.world.block.type.BlockTypes;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
            world = MinecraftServer.getInstanceManager().createInstanceContainer();
            world.setGenerator(new AbsorbGenerator());
        }
        MinecraftServer.getInstanceManager().registerInstance(world);
        final var finalWorld = world;

        registerEvents(finalWorld);

        server.start(configManager.connection().getIp(), configManager.connection().getPort());
        System.out.println("Server started");
    }

    private static void registerEvents(InstanceContainer mainWorld) {
        var eventHandler = MinecraftServer.getGlobalEventHandler();

        eventHandler.addListener(PlayerLoginEvent.class, event -> {
            var player = event.getPlayer();
            var currentPos = player.getPosition();
            player.setRespawnPoint(new Pos(currentPos.x(), 6, currentPos.z()));
            event.setSpawningInstance(mainWorld);

            player.setGameMode(GameMode.CREATIVE);
        });

        eventHandler.addListener(PlayerBlockBreakEvent.class, event -> {
            //swaps the none handler block to a handled block
            try {
                var block = BlockTypes.blockTypes()
                        .parallelStream()
                        .filter(blockType -> {
                            var key = blockType.key();
                            var namespace = event.getBlock().namespace();
                            return key.equals(namespace);
                        })
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("Unknown block of " + event.getBlock().namespace().asString()));
                block.onBreakEvent(event);
            } catch (Throwable e) {
                e.printStackTrace();
                throw e;
            }
        });

        eventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            //swaps the none handler block to a handled block
            try {
                var block = BlockTypes.blockTypes()
                        .parallelStream()
                        .filter(blockType -> {
                            var key = blockType.key();
                            var namespace = event.getBlock().namespace();
                            return key.equals(namespace);
                        })
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("Unknown block of " + event.getBlock().namespace().asString()));
                block.onPlaceEvent(event);
            } catch (Throwable e) {
                e.printStackTrace();
                throw e;
            }
        });
    }
}
