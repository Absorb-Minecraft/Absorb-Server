package org.absorb.world.location;

import com.google.errorprone.annotations.CheckReturnValue;
import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;
import org.absorb.world.block.state.BlockState;
import org.absorb.world.block.state.BlockStateImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class Position {

    private final Point point;
    private final Instance world;

    public Position(@NotNull Instance world, @NotNull Point point) {
        this.world = world;
        this.point = point;
    }

    public Position add(@NotNull Direction direction) {
        return this.add(direction, 1);
    }

    public Position add(@NotNull Direction direction, @Range(from = 1, to = Integer.MAX_VALUE) int amount) {
        return new Position(this.world, this.point.add(direction.blockDirection().mul(amount)));
    }

    @CheckReturnValue
    public Position add(@NotNull Point point) {
        return new Position(this.world, this.point.add(point));
    }

    @CheckReturnValue
    public Position add(double x, double y, double z) {
        return new Position(this.world, this.point.add(x, y, z));
    }

    public BlockState blockState() {
        return new BlockStateImpl(this.world.getBlock(this.point));
    }

    public Position setBlockState(@NotNull BlockState blockState) {
        return setBlockState(blockState, true);
    }

    public Position setBlockState(@NotNull BlockState blockState, boolean runBlockUpdate) {
        this.world.setBlock(this.point, ((BlockStateImpl) blockState).minestom(), runBlockUpdate);
        return this;
    }

    public Point point() {
        return point;
    }

    public Instance instanceContainer() {
        return this.world;
    }
}
