package org.absorb.config.connection;

import org.absorb.AbsorbServer;
import org.absorb.config.Config;
import org.absorb.config.node.Node;
import org.absorb.utils.Else;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class ConnectionConfig implements Config {

    private final HoconConfigurationLoader loader;
    private final File file;
    private final CommentedConfigurationNode rootNode;

    public ConnectionConfig() {
        this.file = new File(AbsorbServer.platform().configsFolder(), "Connection Properties.conf");
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

    public String getIp() {
        return ConnectionConfigNodes.IP.value(this);
    }

    public int getPort() {
        return ConnectionConfigNodes.PORT.value(this);
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
        return Arrays.asList(ConnectionConfigNodes.IP, ConnectionConfigNodes.PORT);
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
