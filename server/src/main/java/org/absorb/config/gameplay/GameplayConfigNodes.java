package org.absorb.config.gameplay;

import org.absorb.config.node.Node;
import org.absorb.utils.Else;

interface GameplayConfigNodes {

    Node<Integer> VIEW_DISTANCE = Node
            .builder()
            .setInteger()
            .setDefaultValue(10)
            .setNodePath("distance", "view")
            .setVerify((value) -> Else.tryFail(() -> value >= 2 && value <= 32, t -> new IllegalStateException("Simulate distance must be between 2 and 32")))
            .setComment("The amount of chunks to send to a player. Higher the number the more processing needed on the server. Valid value is between 2 and 32")
            .build();

    Node<Integer> SIMULATION_DISTANCE = Node
            .builder()
            .setInteger()
            .setDefaultValue(10)
            .setNodePath("distance", "simulate")
            .setComment("The amount of chunks away from a player that entities move in. Higher the number the more processing needed on the server. Valid value is between 2 and 32")
            .setVerify((value) -> Else.tryFail(() -> value >= 2 && value <= 32, t -> new IllegalStateException("Simulate distance must be between 2 and 32")))
            .build();

    Node<Boolean> HARDCORE = Node
            .builder()
            .setBoolean()
            .setDefaultValue(false)
            .setComment("When a player dies, they are banned from the server if true")
            .setNodePath("mode", "hardcore")
            .build();

}
