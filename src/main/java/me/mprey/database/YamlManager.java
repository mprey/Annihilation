package me.mprey.database;

import me.mprey.Annihilation;
import me.mprey.stats.MapStatistics;
import me.mprey.stats.PlayerStatistics;
import me.mprey.stats.Statistics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Mason Prey on 2/9/16.
 */
public class YamlManager {

    public static String YAML_DIR = "database", MAPS_DIR = "map_stats", PLAYERS_DIR = "player_stats";

    private File mapDir, playerDir;

    public YamlManager() {
        this.mapDir = new File(Annihilation.getInstance().getDataFolder() + File.separator + YAML_DIR + File.separator + MAPS_DIR);
        this.playerDir = new File(Annihilation.getInstance().getDataFolder() + File.separator + YAML_DIR + File.separator + PLAYERS_DIR);
    }

    public void init() {
        if (!this.playerDir.exists()) {
            try {
                this.playerDir.mkdirs();
                this.playerDir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!this.mapDir.exists()) {
            try {
                this.mapDir.mkdirs();
                this.mapDir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean exists(Statistics stats) {
        return getFile(stats).exists();
    }

    public void newEntry(Statistics stats) {
        File file = getFile(stats);
        file.mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
                stats.copyDefaults(YamlConfiguration.loadConfiguration(file));
                save(stats);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStatistics(Statistics stats) {
        stats.updateWithConfig(YamlConfiguration.loadConfiguration(getFile(stats)));
        save(stats);
    }

    public void loadStatistics(Statistics stats) {
        stats.updateWithConfig(YamlConfiguration.loadConfiguration(getFile(stats)));
    }

    public void save(Statistics stats) {
        File statsFile = getFile(stats);
        try {
            YamlConfiguration.loadConfiguration(statsFile).save(statsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile(Statistics stats) {
        return new File(Annihilation.getInstance().getDataFolder() + File.separator + YAML_DIR + File.separator + stats.getYAMLDir() + File.separator + stats.getKeyValue() + ".yml");
    }

}
