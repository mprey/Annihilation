package me.mprey.database;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by Mason Prey on 2/9/16.
 */
public class YamlManager {

    public static String YAML_FILE = "db.yml";

    private File file;

    private FileConfiguration config;

    public YamlManager(File file) {
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
    }

}
