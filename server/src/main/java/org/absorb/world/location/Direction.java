package org.absorb.world.location;

import net.minestom.server.coordinate.Vec;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Direction {

    NORTH(new Vec(0, 0, -1)),
    EAST(new Vec(1, 0, 0)),
    SOUTH(new Vec(0, 0, 1)),
    WEST(new Vec(-1, 0, 0)),
    UP(new Vec(0, 1, 0)),
    DOWN(new Vec(0, -1, 0));

    private final @NotNull Vec blockRelative;


    Direction(@NotNull Vec blockRelative) {
        this.blockRelative = blockRelative;
    }

    public Direction opposite(){
        return valueOf(-this.blockRelative.blockX(), -this.blockRelative.blockY(), -this.blockRelative.blockZ());
    }

    public static Stream<Direction> fourFacingValues(boolean includeHeight) {
        if (includeHeight) {
            return Stream.of(NORTH, EAST, SOUTH, WEST, UP, DOWN);
        }
        return Stream.of(NORTH, EAST, SOUTH, WEST);
    }

    public Vec blockDirection() {
        return this.blockRelative;
    }

    public static @NotNull Direction valueOf(int x, int y, int z){
        return valueOf(new Vec(x, y, z));
    }

    public static @NotNull Direction valueOf(Vec vec){
        return Arrays.stream(values()).filter(direction -> direction.blockDirection().equals(vec)).findAny().orElseThrow(() -> new IllegalArgumentException("vector must have only 1 (or -1) and/or 0"));
    }

}
