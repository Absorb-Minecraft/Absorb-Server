package org.absorb.world.block.type;

import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;
import org.absorb.world.block.state.BlockState;
import org.absorb.world.block.state.BlockStateImpl;
import org.absorb.world.item.type.ItemType;
import org.absorb.world.location.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractBlockType implements BlockType {

    private final Block defaultBlock;

    public AbstractBlockType(@NotNull Block defaultBlock) {
        this.defaultBlock = defaultBlock;
    }

    @Override
    public @NotNull NamespaceID key() {
        return this.defaultBlock.registry().namespace();
    }

    @Override
    public double hardness() {
        return this.defaultBlock.registry().hardness();
    }

    @Override
    public int lightEmission() {
        return this.defaultBlock.registry().lightEmission();
    }

    @Override
    public boolean transparent() {
        return this.defaultBlock.registry().occludes();
    }

    @Override
    public boolean flammable() {
        return false;
    }

    @Override
    public boolean lavaFlammable() {
        return false;
    }

    @Override
    public double breakTime() {
        return 0.05;
    }

    @Override
    public boolean breaksByWater() {
        return false;
    }

    @Override
    public boolean breaksByLava() {
        return false;
    }

    @Override
    public boolean breaksByPiston() {
        return false;
    }

    @Override
    public Optional<ItemType> item() {
        return Optional.empty();
    }

    @Override
    public Stream<BlockState> states() {
        return this.defaultBlock.possibleStates().parallelStream().map(BlockStateImpl::new);
    }

    @Override
    public BlockState defaultState() {
        return new BlockStateImpl(this.defaultBlock);
    }

    @Override
    public double blastResistance() {
        return this.defaultBlock.registry().explosionResistance();
    }

    @Override
    public boolean redstoneConnects() {
        return false;
    }

    @Override
    public @NotNull BlockState state(@NotNull Block block) {
        return states().filter(blockState -> {
            var blockStateProperties = ((BlockStateImpl) blockState).minestom().properties();
            return blockStateProperties.equals(block.properties());
        }).findAny().orElseThrow(() -> new RuntimeException("Missing blockstate for " + block.name()));
    }

    @Override
    public void onPlaceEvent(PlayerBlockPlaceEvent event) {
    }

    @Override
    public void onBreakEvent(PlayerBlockBreakEvent event) {

    }

    @Override
    public int redstonePower(BlockState state) {
        return 0;
    }

    @Override
    public boolean isRedstoneConnected(BlockState itsState, Direction from, BlockState thisState) {
        return false;
    }

    @Override
    public int hashCode() {
        return this.defaultBlock.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockType type)) {
            return false;
        }
        return type.key().equals(this.key());
    }
}
