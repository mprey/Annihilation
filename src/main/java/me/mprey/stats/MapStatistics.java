package me.mprey.stats;

import me.mprey.database.MySQLConfig;
import me.mprey.database.MySQLManager;
import me.mprey.database.YamlManager;
import me.mprey.game.TeamColor;
import me.mprey.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapStatistics extends Statistics {

    private static List<MapStatistics> statisticsList = new ArrayList<>();

    public static MapStatistics getStatistics(Map map) {
        for (MapStatistics statistics : statisticsList) {
            if (statistics.getMap() != null && statistics.getMap().equals(map)) {
                return statistics;
            }
        }
        MapStatistics mapStatistics = new MapStatistics(map);
        mapStatistics.load();
        statisticsList.add(mapStatistics);
        return mapStatistics;
    }

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

    public void addGamePlayed() {
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

    public void setDefault() {
        this.gamesPlayed = 0;
        this.totalDeaths = 0;
        this.totalKills = 0;
        this.totalNexusDamage = 0;
        this.greenVictories = 0;
        this.redVictories = 0;
        this.yellowVictories = 0;
        this.blueVictories = 0;
    }

    public String getKeyField() {
        return "name";
    }

    public String getKeyValue() {
        return this.map == null ? "" : this.map.getName();
    }

    public String getTableName() {
        return MySQLManager.MAPS_TABLE;
    }

    public String getInsertQuery() {
        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(MySQLManager.MAPS_TABLE + " (name) VALUES (");
        builder.append("'" + getKeyValue() + "'");
        builder.append(");");
        return builder.toString();
    }

    public String getUpdateQuery() {
        StringBuilder builder = new StringBuilder("UPDATE ");
        builder.append(MySQLManager.MAPS_TABLE + " SET ");
        builder.append("games_played=" + gamesPlayed + ",");
        builder.append("total_deaths=" + totalDeaths + ",");
        builder.append("total_kills=" + totalKills + ",");
        builder.append("total_nexus_damage=" + totalNexusDamage + ",");
        builder.append("green_wins=" + greenVictories + ",");
        builder.append("blue_wins=" + blueVictories + ",");
        builder.append("red_wins=" + redVictories + ",");
        builder.append("yellow_wins=" + yellowVictories + ",");
        builder.append(";");
        return builder.toString();
    }

    public void updateWithResultSet(ResultSet rs) {
        try {
            this.gamesPlayed = rs.getInt("games_played");
            this.totalDeaths = rs.getInt("total_deaths");
            this.totalKills = rs.getInt("total_kills");
            this.totalNexusDamage = rs.getInt("total_nexus_damage");
            this.greenVictories = rs.getInt("green_wins");
            this.blueVictories = rs.getInt("blue_wins");
            this.redVictories = rs.getInt("red_wins");
            this.yellowVictories = rs.getInt("yellow_wins");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getYAMLDir() {
        return YamlManager.MAPS_DIR;
    }

    public void copyDefaults(FileConfiguration config) {
        config.set("games_played", this.gamesPlayed);
        config.set("total_deaths", this.totalDeaths);
        config.set("total_kills", this.totalKills);
        config.set("total_nexus_damage", this.totalNexusDamage);
        config.set("green_wins", this.greenVictories);
        config.set("blue_wins", this.blueVictories);
        config.set("red_wins", this.redVictories);
        config.set("yellow_wins", this.yellowVictories);
    }

    public void updateYAML(FileConfiguration config) {
        config.set("games_played", this.gamesPlayed);
        config.set("total_deaths", this.totalDeaths);
        config.set("total_kills", this.totalKills);
        config.set("total_nexus_damage", this.totalNexusDamage);
        config.set("green_wins", this.greenVictories);
        config.set("blue_wins", this.blueVictories);
        config.set("red_wins", this.redVictories);
        config.set("yellow_wins", this.yellowVictories);
    }

    public void updateWithConfig(FileConfiguration config) {
        this.gamesPlayed = config.getInt("games_played");
        this.totalDeaths = config.getInt("total_deaths");
        this.totalKills = config.getInt("total_kills");
        this.totalNexusDamage = config.getInt("total_nexus_damage");
        this.greenVictories = config.getInt("green_wins");
        this.blueVictories = config.getInt("blue_wins");
        this.redVictories = config.getInt("red_wins");
        this.yellowVictories = config.getInt("yellow_wins");
    }
}
