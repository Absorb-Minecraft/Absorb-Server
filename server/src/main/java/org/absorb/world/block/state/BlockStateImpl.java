package org.absorb.world.block.state;

import net.minestom.server.instance.block.Block;
import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.type.BlockType;
import org.absorb.world.block.type.BlockTypes;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BlockStateImpl implements BlockState {

    private final Block state;

    public BlockStateImpl(Block state) {
        this.state = state;
    }

    public Block minestom() {
        return this.state;
    }

    @Override
    public BlockType type() {
        return BlockTypes.blockTypes().parallelStream().filter(type -> type.key().equals(this.state.namespace())).findAny().orElseThrow(() -> new RuntimeException("Cannot find a blocktype with the id of " + this.state.registry().namespace()));
    }

    @Override
    public <T> Optional<T> value(BlockProperty<T> property) {
        return Optional.of(property.value(this.state));
    }

    @Override
    public <T> Optional<BlockState> with(BlockProperty.Writable<T> property, T value) {
        try {
            return Optional.of(property.withValue(this.state, value)).map(BlockStateImpl::new);
        }catch (IllegalArgumentException e){
            return Optional.empty();
        }
    }

    @Override
    public Map<BlockProperty<?>, ?> properties() {
        return type().properties().collect(Collectors.toMap(property -> property, property -> property.value(this.state)));
    }
}
