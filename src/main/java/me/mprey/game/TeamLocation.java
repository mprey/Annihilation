package me.mprey.game;

import me.mprey.game.map.Region;
import org.bukkit.Location;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class TeamLocation {

    private Region nexus;
    private Location[] spawns;
    private Location enderFurnace, enderBrewer, enderChest;

    public TeamLocation(Region nexus, Location[] spawns, Location enderFurnace, Location enderBrewer, Location enderChest) {
        this.nexus = nexus;
        this.spawns = spawns;
        this.enderBrewer = enderBrewer;
        this.enderChest = enderChest;
        this.enderFurnace = enderFurnace;
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

    public Location[] getSpawns() {
        return spawns;
    }
}
