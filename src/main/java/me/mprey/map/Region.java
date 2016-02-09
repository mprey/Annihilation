package me.mprey.map;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Region {

    private Location maxCorner, minCorner;

    public Region(Location cornerOne, Location cornerTwo) {
        this.maxCorner = getMaximumCorner(cornerOne, cornerTwo);
        this.minCorner = getMinimumCorner(cornerOne, cornerTwo);
    }

    public World getWorld() {
        return minCorner.getWorld();
    }

    public Location getMaxCorner() {
        return maxCorner;
    }

    public Location getMinCorner() {
        return minCorner;
    }

    private Location getMinimumCorner(Location cornerOne, Location cornerTwo) {
        return new Location(this.getWorld(), Math.min(cornerOne.getBlockX(),
                cornerTwo.getBlockX()),
                Math.min(cornerOne.getBlockY(), cornerTwo.getBlockY()), Math.min(
                cornerOne.getBlockZ(), cornerTwo.getBlockZ()));
    }

    private Location getMaximumCorner(Location cornerOne, Location cornerTwo) {
        return new Location(this.getWorld(), Math.max(cornerOne.getBlockX(),
                cornerTwo.getBlockX()),
                Math.max(cornerOne.getBlockY(), cornerTwo.getBlockY()), Math.max(
                cornerOne.getBlockZ(), cornerTwo.getBlockZ()));
    }

    public boolean isWithin(Location input) {
        if (input.getWorld().getName().equals(getWorld().getName())) {
            return (input.getBlockX() >= this.minCorner.getBlockX()
                    && input.getBlockX() <= this.maxCorner.getBlockX()
                    && input.getBlockY() >= this.minCorner.getBlockY()
                    && input.getBlockY() <= this.maxCorner.getBlockY()
                    && input.getBlockZ() >= this.minCorner.getBlockZ()
                    && input.getBlockZ() <= this.maxCorner.getBlockZ());
        }
        return false;
    }

    public boolean isWithin(Chunk chunk) {
        return chunk.getX() >= this.minCorner.getX()
                && chunk.getX() <= this.maxCorner.getX()
                && chunk.getZ() >= this.minCorner.getZ()
                && chunk.getZ() <= this.maxCorner.getZ());
    }

    public boolean isValid() {
        return maxCorner != null && minCorner != null && maxCorner.getWorld().getName().equals(minCorner.getWorld().getName());
    }

}
