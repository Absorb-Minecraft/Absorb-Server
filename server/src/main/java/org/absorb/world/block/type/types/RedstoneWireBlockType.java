package org.absorb.world.block.type.types;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.ExecutionType;
import org.absorb.world.block.property.BlockProperties;
import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.state.BlockStateImpl;
import org.absorb.world.block.type.AbstractBlockType;
import org.absorb.world.block.type.BlockType;
import org.absorb.world.block.update.RedstoneWireUpdate;
import org.absorb.world.location.Position;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class RedstoneWireBlockType extends AbstractBlockType implements BlockType {


    public RedstoneWireBlockType() {
        super(Block.REDSTONE_WIRE);
    }


    @Override
    public void onPlaceEvent(PlayerBlockPlaceEvent event) {
        var block = (BlockStateImpl) RedstoneWireUpdate.placeWire(new Position(event.getInstance(), event.getBlockPosition()), new BlockStateImpl(event.getBlock()));
        event.setBlock(block.minestom());
    }

    @Override
    public void onBreakEvent(PlayerBlockBreakEvent event) {
        MinecraftServer.getSchedulerManager().scheduleNextTick(() -> {
            RedstoneWireUpdate.tickUpdate(new Position(event.getInstance(), event.getBlockPosition()));
        }, ExecutionType.ASYNC);
    }

    @Override
    public @NotNull Stream<BlockProperty<?>> properties() {
        return Stream.of(
                BlockProperties.POWER,
                BlockProperties.CONNECTED_NORTH_REDSTONE,
                BlockProperties.CONNECTED_EAST_REDSTONE,
                BlockProperties.CONNECTED_SOUTH_REDSTONE,
                BlockProperties.CONNECTED_WEST_REDSTONE);
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
