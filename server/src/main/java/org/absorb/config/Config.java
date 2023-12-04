package org.absorb.config;

import org.absorb.config.node.Node;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.util.Collection;

public interface Config {

    ConfigurationLoader<? extends CommentedConfigurationNode> loader();

    CommentedConfigurationNode rootNode();

    File file();

    Collection<Node<?>> nodes();

    void updateMissing();
}
