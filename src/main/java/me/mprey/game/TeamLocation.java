package me.mprey.game;

import me.mprey.map.Region;
import me.mprey.util.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
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
        this.nexus = new Region(deserialize.get(nexus));
        this.spawns = ConfigUtil.deserializeLocationArray((Map<String, Object>) deserialize.get("spawns"));
        this.enderFurnace = ConfigUtil.deserializeLocation(deserialize.get("ender_furnace"));
        this.enderBrewer = ConfigUtil.deserializeLocation("ender_brewer");
        this.enderChest = ConfigUtil.deserializeLocation("ender_chest");
    }

    public TeamLocation(Object object) {
        this((Map<String, Object>) object);
    }

    public Region getNexus() {
        if (nexus == null) {
            nexus = new Region(null, null);
        }
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
        if (spawns == null) {
            spawns = new ArrayList<>();
        }
        return spawns;
    }

    public boolean check() {
        return getNexus().isValid() &&
                getSpawns().size() > 0 &&
                getEnderFurnace() != null &&
                getEnderBrewer() != null &&
                getEnderChest() != null;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> serialize = new HashMap<>();

        serialize.put("region", getNexus().serialize());
        serialize.put("spawns", ConfigUtil.serializeLocationArray(getSpawns()));
        serialize.put("ender_furnace", ConfigUtil.serializeLocation(getEnderFurnace()));
        serialize.put("ender_brewer", ConfigUtil.serializeLocation(getEnderBrewer()));
        serialize.put("ender_chest", ConfigUtil.serializeLocation(getEnderChest()));

        return null;
    }
}
