package me.mprey.stats;

import me.mprey.Annihilation;
import me.mprey.database.MySQLManager;
import me.mprey.util.UUIDFetcher;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class PlayerStatistics extends Statistics {

    private static List<PlayerStatistics> statisticsList = new ArrayList<>();

    public static PlayerStatistics getStatistics(Player player) {
        for (PlayerStatistics statistics : statisticsList) {
            if (statistics.getUUID() != null && statistics.getUUID().equals(player.getUniqueId())) {
                return statistics;
            }
        }
        PlayerStatistics playerStatistics = new PlayerStatistics(player);
        playerStatistics.load();
        statisticsList.add(playerStatistics);
        return playerStatistics;
    }

    private int nexusDamage, kills, deaths, nexusKills, gamesPlayed, wins, losses;

    private OfflinePlayer player;

    private UUID uuid;

    public PlayerStatistics(OfflinePlayer player) {
        this.player = player;
        if (this.player.isOnline()) {
            this.uuid = player.getUniqueId();
        }
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        this.kills++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        this.deaths++;
    }

    public int getNexusDamage() {
        return this.nexusDamage;
    }

    public void addNexusDamage(int amt) {
        this.nexusDamage += amt;
    }

    public int getNexusKills() {
        return nexusKills;
    }

    public void addNexusKill() {
        this.nexusKills++;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void addGamePlayed() {
        this.gamesPlayed++;
    }

    public int getWins() {
        return wins;
    }

    public void addWin() {
        this.wins++;
    }

    public int getLosses() {
        return losses;
    }

    public void addLosses() {
        this.losses++;
    }

    public String getUUID() {
        if (this.uuid == null) {
            try {
                if(this.player.isOnline()) {
                    this.uuid = this.player.getPlayer().getUniqueId();
                } else {
                    this.uuid = this.player.getUniqueId();
                }
            } catch(Exception ex) {
                try {
                    this.uuid = UUIDFetcher.getUUIDOf(this.player.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this.uuid.toString();
    }

    public void setDefault() {
        this.kills = 0;
        this.deaths = 0;
        this.nexusKills = 0;
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.nexusDamage = 0;
    }

    public String getKeyField() {
        return "uuid";
    }

    public String getKeyValue() {
        return this.player == null ? "" : this.getUUID();
    }

    public String getTableName() {
        return MySQLManager.PLAYERS_TABLE;
    }

    @Override
    public String getInsertQuery() {
        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append(MySQLManager.PLAYERS_TABLE + " (uuid) VALUES (");
        builder.append("'" + this.getKeyValue() + "'");
        builder.append(");");
        return builder.toString();
    }

    @Override
    public String getUpdateQuery() {
        StringBuilder builder = new StringBuilder("UPDATE ");
        builder.append(MySQLManager.PLAYERS_TABLE + " SET ");
        builder.append("kills=" + kills + ",");
        builder.append("deaths=" + deaths + ",");
        builder.append("nexus_kills=" + nexusKills + ",");
        builder.append("nexus_damage=" + nexusDamage + ",");
        builder.append("games_played=" + gamesPlayed + ",");
        builder.append("wins=" + wins + ",");
        builder.append("losses=" + losses + ",");
        return builder.toString();
    }

    @Override
    public void updateWithResultSet(ResultSet rs) {
        try {
            this.kills = rs.getInt("kills");
            this.deaths = rs.getInt("deaths");
            this.nexusKills = rs.getInt("nexus_kills");
            this.nexusDamage = rs.getInt("nexus_damage");
            this.gamesPlayed = rs.getInt("games_played");
            this.wins = rs.getInt("wins");
            this.losses = rs.getInt("losses");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getYAMLDir() {
        return null;
    }

    public void copyDefaults(FileConfiguration config) {
        config.addDefault("kills", this.kills);
        config.addDefault("deaths", this.deaths);
        config.addDefault("nexus_kills", this.nexusKills);
        config.addDefault("nexus_damage", this.nexusDamage);
        config.addDefault("games_played", this.gamesPlayed);
        config.addDefault("wins", this.wins);
        config.addDefault("losses", this.losses);
    }

    public void updateYAML(FileConfiguration config) {
        config.set("kills", this.kills);
        config.set("deaths", this.deaths);
        config.set("nexus_kills", this.nexusKills);
        config.set("nexus_damage", this.nexusDamage);
        config.set("games_played", this.gamesPlayed);
        config.set("wins", this.wins);
        config.set("losses", this.losses);
    }

    public void updateWithConfig(FileConfiguration config) {
        this.kills = config.getInt("kills");
        this.deaths = config.getInt("deaths");
        this.nexusKills = config.getInt("nexus_kills");
        this.nexusDamage = config.getInt("nexus_damage");
        this.gamesPlayed = config.getInt("games_played");
        this.wins = config.getInt("wins");
        this.losses = config.getInt("losses");
    }
}
