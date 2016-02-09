package me.mprey.stats;

import me.mprey.game.TeamColor;
import me.mprey.map.Map;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapStatistics {

    private int gamesPlayed, totalDeaths, totalKills, totalNexusDamage, greenVictories, blueVictories, yellowVictories, redVictories;

    private Map map;

    public MapStatistics(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

}
