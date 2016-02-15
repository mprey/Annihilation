package me.mprey.game;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class GameManager {

    public static final String GAMES_PATH = "games";

    private ArrayList<Game> gameList;
    private Map<Player, Game> playerGameMap;

    public GameManager() {
        this.gameList = new ArrayList<>();
        this.playerGameMap = new HashMap<>();
    }



}
