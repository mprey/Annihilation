package me.mprey.stats;

import me.mprey.database.MySQLConfig;
import me.mprey.database.MySQLManager;
import me.mprey.game.TeamColor;
import me.mprey.map.Map;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapStatistics extends Statistics {

    private int gamesPlayed, totalDeaths, totalKills, totalNexusDamage, greenVictories, blueVictories, yellowVictories, redVictories;

    private Map map;

    public MapStatistics(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public int getTotalNexusDamage() {
        return totalNexusDamage;
    }

    public int getVictories(TeamColor teamColor) {
        switch (teamColor) {
            case RED:
                return redVictories;
            case BLUE:
                return blueVictories;
            case YELLOW:
                return yellowVictories;
            case GREEN:
                return greenVictories;
            default:
                return 0;
        }
    }

    public void addVictory(TeamColor teamColor) {
        switch (teamColor) {
            case RED:
                redVictories++;
                break;
            case BLUE:
                blueVictories++;
                break;
            case YELLOW:
                yellowVictories++;
                break;
            case GREEN:
                greenVictories++;
                break;
            default:
                break;
        }
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public void addDeath() {
        totalDeaths++;
    }

    public void addKill() {
        totalKills++;
    }

    public void addNexusDamage(int amt) {
        totalNexusDamage += amt;
    }

    public void load() {

    }

    public void update() {

    }

    public String getTableName() {
        return MySQLManager.DATABASE_PREFIX + "map";
    }

    public String insertQuery() {
        return null;
    }

    public String updateQuery() {
        return null;
    }
}
