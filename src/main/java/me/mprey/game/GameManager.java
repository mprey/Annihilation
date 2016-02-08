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

    public void loadGames() {

    }

    private void loadGame(YamlConfiguration config) {
        //blue spawn
        //red spawn
        //yellow spawn
        //green spawn
        //lobby spawn
        //sign locations
        //holo locations
        //boss spawns
        //boss rewards
        //blue enderchest, enderbrewer, enderfurnace, nexus, blue region
        //yellow enderchest, enderbrewer, enderfurnace, nexus, yellow region
        //green enderchest, enderbrewer, enderfurance, nexus, green region
        //red enderchest, enderbrewer, enderfurnace, nexus, red region
        //diamond locations
    }

}
