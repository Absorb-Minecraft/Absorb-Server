package org.absorb.config.connection;

import org.absorb.config.node.Node;

interface ConnectionConfigNodes {

    Node<String> IP = Node.builder().setNodePath("server", "ip").setDefaultValue("").setString().build();
    Node<Integer> PORT = Node.builder().setNodePath("server", "port").setDefaultValue(25565).setInteger().build();

}
