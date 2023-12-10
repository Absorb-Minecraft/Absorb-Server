package org.absorb.world.block.state;

import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.type.BlockType;

import java.util.Map;
import java.util.Optional;

public interface BlockState {

    BlockType type();

    <T> Optional<T> value(BlockProperty<T> property);

    <T> Optional<BlockState> with(BlockProperty.Writable<T> property, T value);

    Map<BlockProperty<?>, ?> properties();
}
