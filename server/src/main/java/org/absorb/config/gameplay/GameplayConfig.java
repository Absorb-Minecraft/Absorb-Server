package org.absorb.config.gameplay;

import org.absorb.AbsorbServer;
import org.absorb.config.Config;
import org.absorb.config.node.Node;
import org.absorb.utils.Else;
import org.jetbrains.annotations.Range;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class GameplayConfig implements Config {

    private final HoconConfigurationLoader loader;
    private final File file;
    private final CommentedConfigurationNode rootNode;

    public GameplayConfig() {
        this.file = new File(AbsorbServer.platform().configsFolder(), "Gameplay.conf");
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

    public @Range(from = 2, to = 32) int viewDistance() {
        return GameplayConfigNodes.VIEW_DISTANCE.value(this);
    }

    public void setViewDistance(@Range(from = 2, to = 32) int distance) throws SerializationException {
        GameplayConfigNodes.VIEW_DISTANCE.setValue(this, distance);
    }

    public @Range(from = 2, to = 32) int simulationDistance() {
        return GameplayConfigNodes.SIMULATION_DISTANCE.value(this);
    }

    public void setSimulationDistance(@Range(from = 2, to = 32) int distance) throws SerializationException {
        GameplayConfigNodes.SIMULATION_DISTANCE.setValue(this, distance);
    }

    public boolean hardcore() {
        return GameplayConfigNodes.HARDCORE.value(this);
    }

    public void setHardcore(boolean hardcore) throws SerializationException {
        GameplayConfigNodes.HARDCORE.setValue(this, hardcore);
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

    @Override
    public Collection<Node<?>> nodes() {
        return Arrays.asList(GameplayConfigNodes.HARDCORE, GameplayConfigNodes.SIMULATION_DISTANCE, GameplayConfigNodes.VIEW_DISTANCE);
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
