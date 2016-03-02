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
        init();
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
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileConfiguration config = getConfiguration(stats);
                stats.copyDefaults(config);
                save(stats, config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStatistics(Statistics stats) {
        FileConfiguration config = getConfiguration(stats);
        stats.updateWithConfig(config);
        save(stats, config);
    }

    public void loadStatistics(Statistics stats) {
        stats.updateWithConfig(YamlConfiguration.loadConfiguration(getFile(stats)));
    }

    public void save(Statistics stats, FileConfiguration config) {
        File statsFile = getFile(stats);
        try {
            config.save(statsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile(Statistics stats) {
        return new File(Annihilation.getInstance().getDataFolder() + File.separator + YAML_DIR + File.separator + stats.getYAMLDir() + File.separator + stats.getKeyValue() + ".yml");
    }

    private FileConfiguration getConfiguration(Statistics stats) {
        return YamlConfiguration.loadConfiguration(getFile(stats));
    }

}
