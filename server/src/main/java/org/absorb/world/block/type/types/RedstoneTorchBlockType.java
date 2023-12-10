package org.absorb.world.block.type.types;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.ExecutionType;
import org.absorb.world.block.property.BlockProperties;
import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.state.BlockState;
import org.absorb.world.block.state.BlockStateImpl;
import org.absorb.world.block.type.AbstractBlockType;
import org.absorb.world.block.type.BlockType;
import org.absorb.world.block.update.RedstoneWireUpdate;
import org.absorb.world.location.Direction;
import org.absorb.world.location.Position;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class RedstoneTorchBlockType extends AbstractBlockType implements BlockType {


    public RedstoneTorchBlockType() {
        super(Block.REDSTONE_TORCH);
    }

    @Override
    public int redstonePower(BlockState state) {
        return BlockProperties.LIT.value(((BlockStateImpl) state).minestom()) ? 7 : 0;
    }

    @Override
    public void onPlaceEvent(PlayerBlockPlaceEvent event) {
        MinecraftServer.getSchedulerManager().scheduleNextTick(() -> {
            var position = new Position(event.getInstance(), event.getBlockPosition());
            RedstoneWireUpdate.updateRedstoneWireStates(position, true);
            RedstoneWireUpdate.updateRelativePower(position);
        }, ExecutionType.ASYNC);
    }

    @Override
    public void onBreakEvent(PlayerBlockBreakEvent event) {
        MinecraftServer.getSchedulerManager().scheduleNextTick(() -> {
            var position = new Position(event.getInstance(), event.getBlockPosition());
            RedstoneWireUpdate.updateRedstoneWireStates(position, false);
            RedstoneWireUpdate.updateRelativePower(position);
        }, ExecutionType.ASYNC);
    }

    @Override
    public @NotNull Stream<BlockProperty<?>> properties() {
        return Stream.of(BlockProperties.LIT);
    }

    @Override
    public boolean breaksByWater() {
        return true;
    }

    @Override
    public boolean breaksByLava() {
        return true;
    }

    @Override
    public boolean breaksByPiston() {
        return true;
    }

    @Override
    public boolean redstoneConnects() {
        return true;
    }

    @Override
    public boolean isRedstoneConnected(BlockState itsState, Direction from, BlockState thisState) {
        return from != Direction.DOWN;
    }
}
