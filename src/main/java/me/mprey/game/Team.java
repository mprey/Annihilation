package me.mprey.game;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Team implements ConfigurationSerializable {

    private Location[] spawnLocations;
    private Block enderChest, enderBrewer, enderFurnace;
    private Nexus nexus;
    private Region region;

    private List<Player> playerList;

    public Team(TeamColor color) {

    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("")

        return null;
    }

}
