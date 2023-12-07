package org.absorb.world.block.type.types;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.ExecutionType;
import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.type.AbstractBlockType;
import org.absorb.world.block.type.BlockType;
import org.absorb.world.block.update.RedstoneWireUpdate;
import org.absorb.world.location.Position;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class RedstoneTorchBlockType extends AbstractBlockType implements BlockType {


    public RedstoneTorchBlockType() {
        super(Block.REDSTONE_TORCH);
    }

    @Override
    public void onPlaceEvent(PlayerBlockPlaceEvent event) {
        MinecraftServer.getSchedulerManager().scheduleNextTick(() -> {
            RedstoneWireUpdate.tickUpdate(new Position(event.getInstance(), event.getBlockPosition()));
        }, ExecutionType.ASYNC);
    }

    @Override
    public void onBreakEvent(PlayerBlockBreakEvent event) {
        MinecraftServer.getSchedulerManager().scheduleNextTick(() -> {
            RedstoneWireUpdate.tickUpdate(new Position(event.getInstance(), event.getBlockPosition()));
        }, ExecutionType.ASYNC);
    }

    @Override
    public @NotNull Stream<BlockProperty<?>> properties() {
        return Stream.empty();
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
}
