package me.mprey.map;

import me.mprey.Annihilation;
import me.mprey.util.ConfigUtil;
import me.mprey.util.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;

/**
 * Created by Mason Prey on 3/5/16.
 */
public class Lobby {

    public static final String FILE_NAME = "lobby";

    private Location spawn;

    private ItemStack[] hotbar;

    private Scoreboard scoreboard;

    private World world;

    public Lobby(Location spawn, ItemStack[] hotbar) {
        this.spawn = spawn;
        this.hotbar = hotbar;
    }

    public Lobby() {
        this.spawn = null;
        this.hotbar = defaultHotBar();
        this.scoreboard = defaultScoreboard();
    }

    public Lobby(FileConfiguration file) {
        this.spawn = ConfigUtil.deserializeLocation(file.get("spawn"));
    }

    public Lobby(World world) {
        this.spawn = world.getSpawnLocation();
        this.hotbar = defaultHotBar();
        this.world = world;
        this.scoreboard = defaultScoreboard();
    }

    public Location getSpawn() {
        if (spawn == null) {
            if (world == null) {
                return new Location(Bukkit.getWorlds().get(0), 0D, 0D, 0D);
            }
            return this.world.getSpawnLocation();
        }
        return this.spawn;
    }

    public void setSpawn(Location loc) {
        this.spawn = loc;
    }

    public World getWorld() {
        if (this.world == null) {
            loadWorld();
        }
        return this.world;
    }

    public boolean isWorldLoaded() {
        for (World world : Annihilation.getInstance().getServer().getWorlds()) {
            if (world.getName().equals("lobby")) {
                return true;
            }
        }
        return false;
    }

    public void copyWorld() {
        File sourceFile = new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR, "lobby");
        File targetFile = new File(Annihilation.getInstance().getServer().getWorldContainer().getAbsolutePath(), "lobby");

        if (targetFile.isDirectory()) {
            if (targetFile.list().length > 0) {
                WorldUtil.deleteWorld("lobby");
            }
        }

        WorldUtil.copyWorld(sourceFile, targetFile);
        if (!WorldUtil.loadWorld("lobby")) {
            //TODO log failure to create world
            Annihilation.getInstance().getLogger().info("FAILURE TO LOAD LOBBY WORLD!");
        }
    }

    public void loadWorld() {
        if (!isWorldLoaded()) {
            copyWorld();
        }
        this.world = Annihilation.getInstance().getServer().getWorld("lobby");
    }

    public boolean saveData() {
        File target = new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR + File.separator + FILE_NAME, MapManager.LOBBY_FILE);
        if (!target.exists()) {
            try {
                target.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(target);
        //TODO write data to config

        try {
            config.save(target);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveWorld() {
        if (isWorldLoaded()) {
            this.world.save();
            WorldUtil.deleteWorld(new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR + File.separator + FILE_NAME));

            File sourceFile = new File(Annihilation.getInstance().getServer().getWorldContainer().getAbsolutePath(), FILE_NAME);
            File targetFile = new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR + File.separator + FILE_NAME);
            WorldUtil.copyWorld(sourceFile, targetFile);
            return true;
        }
        return false;
    }

    public static Scoreboard defaultScoreboard() {
        return null;
    }

    public static ItemStack[] defaultHotBar() {
        ItemStack[] hotbar = new ItemStack[9];
        //TODO populate
        return hotbar;
    }

}
