package org.absorb.config.world;

import net.minestom.server.utils.NamespaceID;
import org.absorb.AbsorbServer;
import org.absorb.config.Config;
import org.absorb.config.node.Node;
import org.absorb.utils.Else;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class WorldConfig implements Config {

    private final HoconConfigurationLoader loader;
    private final File file;
    private final CommentedConfigurationNode rootNode;

    public WorldConfig() {
        this.file = new File(AbsorbServer.platform().configsFolder(), "Worlds.conf");
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.loader = HoconConfigurationLoader.builder().prettyPrinting(true).file(file).build();
        this.rootNode = Else.tryGetOrElse(ConfigurateException.class, this.loader::createNode, this.loader::load);
    }

    @Override
    public ConfigurationLoader<? extends CommentedConfigurationNode> loader() {
        return this.loader;
    }

    @Override
    public CommentedConfigurationNode rootNode() {
        return this.rootNode;
    }

    @Override
    public File file() {
        return this.file;
    }

    public NamespaceID defaultWorld() {
        try {
            return WorldConfigNodes.DEFAULT_WORLD.value(this);
        } catch (IllegalArgumentException e) {
            return NamespaceID.from(AbsorbServer.ABSORB, "flat");
        }
    }

    public void setDefaultWorld(@NotNull NamespaceID resourceKey) throws SerializationException {
        WorldConfigNodes.DEFAULT_WORLD.setValue(this, resourceKey);
    }

    @Override
    public Collection<Node<?>> nodes() {
        return Arrays.asList(WorldConfigNodes.DEFAULT_WORLD);
    }

    @Override
    public void updateMissing() {
        for (Node<?> node : nodes()) {
            updateNode(node);
        }
        try {
            this.loader.save(this.rootNode);
        } catch (ConfigurateException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void updateNode(Node<T> node) {
        try {
            node.value(this);
        } catch (Throwable e) {
            try {
                node.setValue(this, node.defaultValue());
            } catch (SerializationException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
