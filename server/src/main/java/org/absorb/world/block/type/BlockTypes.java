package org.absorb.world.block.type;

import org.absorb.world.block.type.types.AirBlockType;
import org.absorb.world.block.type.types.GrassBlockBlockType;
import org.absorb.world.block.type.types.RedstoneTorchBlockType;
import org.absorb.world.block.type.types.RedstoneWireBlockType;

import java.util.Collection;
import java.util.concurrent.LinkedTransferQueue;

public abstract class BlockTypes {

    private static final LinkedTransferQueue<BlockType> BLOCKS = new LinkedTransferQueue<>();

    public static final AirBlockType AIR = register(new AirBlockType());
    public static final GrassBlockBlockType GRASS_BLOCK = register(new GrassBlockBlockType());
    public static final RedstoneWireBlockType REDSTONE_WIRE = register(new RedstoneWireBlockType());
    public static final RedstoneTorchBlockType REDSTONE_TORCH = register(new RedstoneTorchBlockType());

    public static Collection<BlockType> blockTypes() {
        return BLOCKS;
    }

    private static <BT extends BlockType> BT register(BT type) {
        BLOCKS.add(type);
        return type;
    }
}
