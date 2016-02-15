package me.mprey.game;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Team {

    private List<Player> playerList;
    private TeamColor color;

    public Team(TeamColor color) {
        this.color = color;
        this.playerList = new ArrayList<>();
    }

}
