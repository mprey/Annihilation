package me.mprey.game;

import me.mprey.map.Region;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class TeamLocation implements ConfigurationSerializable {

    private Region nexus;
    private List<Location> spawns;
    private Location enderFurnace, enderBrewer, enderChest;

    public TeamLocation(Region nexus, List<Location> spawns, Location enderFurnace, Location enderBrewer, Location enderChest) {
        this.nexus = nexus;
        this.spawns = spawns;
        this.enderBrewer = enderBrewer;
        this.enderChest = enderChest;
        this.enderFurnace = enderFurnace;
    }

    public TeamLocation(Map<String, Object> deserialize) {

    }

    public Region getNexus() {
        return nexus;
    }

    public Location getEnderChest() {
        return enderChest;
    }

    public Location getEnderBrewer() {
        return enderBrewer;
    }

    public Location getEnderFurnace() {
        return enderFurnace;
    }

    public List<Location> getSpawns() {
        return spawns;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> serialize = new HashMap<>();

        serialize.put("region", this.nexus.serialize());
        serialize.put("")

        return null;
    }
}
