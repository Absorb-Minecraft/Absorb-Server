package org.absorb.world.block.property;

import org.absorb.world.block.property.properties.*;

public class BlockProperties {

    public static final BlockPowerProperty POWER = new BlockPowerProperty();
    public static final RedstoneConnectedEastProperty CONNECTED_EAST_REDSTONE = new RedstoneConnectedEastProperty();
    public static final RedstoneConnectedNorthProperty CONNECTED_NORTH_REDSTONE = new RedstoneConnectedNorthProperty();
    public static final RedstoneConnectedSouthProperty CONNECTED_SOUTH_REDSTONE = new RedstoneConnectedSouthProperty();
    public static final RedstoneConnectedWestProperty CONNECTED_WEST_REDSTONE = new RedstoneConnectedWestProperty();

    public static final LitProperty LIT = new LitProperty();
}
