package org.absorb.world;

import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.world.DimensionType;
import org.absorb.AbsorbServer;
import org.absorb.utils.ThrowableCompletableFuture;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.jackson.JacksonConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletionException;

public class WorldManagerExtensions {

    public static ThrowableCompletableFuture<InstanceContainer, IOException> loadWorldAsynced(String name) {
        var future = new ThrowableCompletableFuture<InstanceContainer, IOException>(IOException.class);
        new Thread(() -> {
            try {
                var world = loadWorldSynced(name);
                future.complete(world);
            } catch (IOException e) {
                throw new CompletionException(e);
            }
        }).start();
        return future;

    }

    public static InstanceContainer loadWorldSynced(String name) throws IOException {
        var folder = AbsorbServer.platform().worldsFolder();
        var worldFolder = new File(folder, name);
        if (!worldFolder.exists()) {
            throw new IOException("World does not exist");
        }
        var anvil = new AnvilLoader(folder.getPath() + "/" + name);
        var entry = getOrCreateId(worldFolder);

        var instance = new InstanceContainer(entry.getKey(), entry.getValue());
        anvil.loadInstance(instance);
        instance.setGenerator(new AbsorbGenerator());
        return instance;
    }

    private static Map.Entry<UUID, DimensionType> getOrCreateId(@NotNull File folder) throws ConfigurateException {
        File absorbWorldFile = new File(folder, "absorb.json");
        try {
            if (absorbWorldFile.exists()) {
                return readAbsorbWorldFile(absorbWorldFile);
            }
        } catch (ConfigurateException e) {
            throw new IllegalStateException("Failed to read " + absorbWorldFile.getPath() + " correctly", e);
        }
        UUID id = UUID.randomUUID();
        writeAbsorbWorldFile(absorbWorldFile, id, DimensionType.OVERWORLD);
        return Map.entry(id, DimensionType.OVERWORLD);
    }

    private static void writeAbsorbWorldFile(@NotNull File file, UUID id, DimensionType type) throws ConfigurateException {
        var configLoader = JacksonConfigurationLoader.builder().file(file).build();
        var root = configLoader.createNode();
        root.node("UUID").set(id);
        root.node("DimensionType").set(type.getName().asString().toLowerCase());
        configLoader.save(root);
    }

    private static Map.Entry<UUID, DimensionType> readAbsorbWorldFile(@NotNull File file) throws ConfigurateException {
        var configLoader = JacksonConfigurationLoader.builder().file(file).build();
        var root = configLoader.load();
        var id = root.node("UUID").get(UUID.class);
        if (id == null) {
            throw new IllegalStateException("Could not read worlds UUID in '" + file.getPath() + "'");
        }
        var typeName = root.node("DimensionType").getString();
        if (typeName == null) {
            throw new IllegalStateException("Could not read worlds dimension type in '" + file.getPath() + "'");
        }
        var type = switch (typeName.toLowerCase()) {
            case "overworld" -> DimensionType.OVERWORLD;
            default ->
                    throw new IllegalStateException("Unknown dimension type of '" + typeName + "' in '" + file.getPath() + "'");
        };
        return Map.entry(id, type);
    }
}
