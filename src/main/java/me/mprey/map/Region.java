package me.mprey.map;

import me.mprey.util.ConfigUtil;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;
import java.util.Map;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Region implements ConfigurationSerializable {

    private Location maxCorner, minCorner;

    public Region(Map<String, Object> deserialize) {
        this(ConfigUtil.deserializeLocation(deserialize.get("max_corner")), ConfigUtil.deserializeLocation("min_corner"));
    }

    public Region(Object object) {
        this((Map<String, Object>) object);
    }

    public Region(Location cornerOne, Location cornerTwo) {
        this.maxCorner = getMaximumCorner(cornerOne, cornerTwo);
        this.minCorner = getMinimumCorner(cornerOne, cornerTwo);
    }

    public World getWorld() {
        if (minCorner != null ) {
            return minCorner.getWorld();
        }
        return null;
    }

    public Location getMaxCorner() {
        return maxCorner;
    }

    public Location getMinCorner() {
        return minCorner;
    }

    public void addLocation(Location loc) {
        //TODO
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
        if (minCorner != null && maxCorner != null && input.getWorld().getName().equals(getWorld().getName())) {
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
        if (minCorner != null && maxCorner != null) {
            return chunk.getX() >= this.minCorner.getX()
                    && chunk.getX() <= this.maxCorner.getX()
                    && chunk.getZ() >= this.minCorner.getZ()
                    && chunk.getZ() <= this.maxCorner.getZ();
        }
        return false;
    }

    public boolean isValid() {
        return maxCorner != null && minCorner != null && maxCorner.getWorld().getName().equals(minCorner.getWorld().getName());
    }

    public Map<String, Object> serialize() {
        Map<String, Object> serialize = new HashMap<>();

        serialize.put("max_corner", ConfigUtil.serializeLocation(maxCorner));
        serialize.put("min_corner", ConfigUtil.serializeLocation(minCorner));

        return serialize;
    }
}
