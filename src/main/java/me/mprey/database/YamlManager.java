package me.mprey.database;

import me.mprey.Annihilation;
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

    public static String YAML_DIR = "database";

    private File dir;

    private HashMap<UUID, File> fileCache;

    public YamlManager() {
        this.dir = new File(Annihilation.getInstance().getDataFolder(), YAML_DIR);
        this.fileCache = new HashMap<>();
        if (!this.dir.exists()) {
            try {
                this.dir.createNewFile();
            } catch (IOException e) {
                //TODO log failure to create file
            }
        }
    }

    public boolean entryExists(Player player) {
        return entryExists(player.getUniqueId());
    }

    public boolean entryExists(UUID uuid) {
        return new File(dir, uuid.toString() + ".yml").exists();
    }

    public void createPlayerFile(Player player) {
        createPlayerFile(player.getUniqueId());
    }

    public File createPlayerFile(UUID uuid) {
        if (!entryExists(uuid)) {
            File playerFile = new File(dir, uuid.toString() + ".yml");
            try {
                if (!playerFile.createNewFile()) {
                    //TODO log unable to create player file
                    return null;
                }
                return fileCache.put(uuid, playerFile);
            } catch (IOException e) {
                //TODO log unable to create
            }
        }
        return fileCache.get(uuid);
    }

    public File getPlayerFile(Player player) {
        return getPlayerFile(player.getUniqueId());
    }

    public File getPlayerFile(UUID uuid) {
        if (fileCache.containsKey(uuid)) {
            return fileCache.get(uuid);
        }
        return createPlayerFile(uuid);
    }

    private void copyDefaults(File file) {

    }

}
