package org.absorb.world.block.type;

import com.google.errorprone.annotations.CheckReturnValue;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import org.absorb.utils.StringIdentifier;
import org.absorb.world.block.property.BlockProperty;
import org.absorb.world.block.state.BlockState;
import org.absorb.world.item.type.ItemType;
import org.absorb.world.location.Direction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public interface BlockType extends StringIdentifier {

    @NotNull Stream<BlockProperty<?>> properties();

    double blastResistance();

    Optional<ItemType> item();

    Stream<BlockState> states();

    BlockState defaultState();

    double hardness();

    int lightEmission();

    boolean transparent();

    boolean flammable();

    boolean lavaFlammable();

    double breakTime();

    boolean breaksByWater();

    boolean breaksByLava();

    boolean breaksByPiston();

    boolean redstoneConnects();

    @CheckReturnValue
    @NotNull BlockState state(@NotNull Block block);

    @ApiStatus.Internal
    void onPlaceEvent(PlayerBlockPlaceEvent event);

    @ApiStatus.Internal
    void onBreakEvent(PlayerBlockBreakEvent event);

    @ApiStatus.Internal
    int redstonePower(BlockState state);

    @ApiStatus.Internal
    boolean isRedstoneConnected(BlockState itsState, Direction from, BlockState thisState);

}
