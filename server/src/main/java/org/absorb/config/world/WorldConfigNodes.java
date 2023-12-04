package org.absorb.config.world;

import net.minestom.server.utils.NamespaceID;
import org.absorb.AbsorbServer;
import org.absorb.config.node.Node;

interface WorldConfigNodes {

    Node<NamespaceID> DEFAULT_WORLD = Node.builder().setResourceKey().setNodePath("world", "default").setDefaultValue(NamespaceID.from(AbsorbServer.ABSORB, "flat")).setComment("The world to load first").build();
}
