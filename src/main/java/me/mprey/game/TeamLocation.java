package me.mprey.game;

import me.mprey.map.Region;
import me.mprey.util.ConfigUtil;
import me.mprey.util.GameUtil;
import me.mprey.util.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
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

    public void setEnderChest(Location loc) {
        this.enderChest = loc;
    }

    public Location getEnderBrewer() {
        return enderBrewer;
    }

    public void setEnderBrewer(Location loc) {
        this.enderBrewer = loc;
    }

    public Location getEnderFurnace() {
        return enderFurnace;
    }

    public void setEnderFurnace(Location loc) {
        this.enderFurnace = loc;
    }

    public List<Location> getSpawns() {
        if (spawns == null) {
            spawns = new ArrayList<>();
        }
        return spawns;
    }

    public void addSpawn(Location location) {
        if (!Utils.containsLocation(getSpawns(), location)) {
            getSpawns().add(location);
        }
    }

    public boolean check() {
        return getNexus().isValid() &&
                getSpawns().size() > 0 &&
                getEnderFurnace() != null &&
                getEnderBrewer() != null &&
                getEnderChest() != null;
    }

    public BaseComponent[] getComponents() {
        return new ComponentBuilder("region=").color(ChatColor.WHITE).bold(true).append(getNexus().toString()).append("\n" + "spawns=").color(ChatColor.WHITE).bold(true).append(GameUtil.arrayToString(getSpawns(), true)).color(ChatColor.GREEN).bold(false)
                .append("\n" + "ender_furnace=").color(ChatColor.WHITE).bold(true).append(GameUtil.locationToString(getEnderFurnace())).color(ChatColor.GREEN).bold(false)
                .append("\n" + "ender_brewer=").color(ChatColor.WHITE).bold(true).append(GameUtil.locationToString(getEnderBrewer())).color(ChatColor.GREEN).bold(false)
                .append("\n" + "ender_chest=").color(ChatColor.WHITE).bold(true).append(GameUtil.locationToString(getEnderChest())).color(ChatColor.GREEN).bold(false)
                .create();
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
