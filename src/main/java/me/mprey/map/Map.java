package me.mprey.map;

import me.mprey.Annihilation;
import me.mprey.game.TeamColor;
import me.mprey.game.TeamLocation;
import me.mprey.util.ConfigUtil;
import me.mprey.util.GameUtil;
import me.mprey.util.Utils;
import me.mprey.util.WorldUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Map implements ConfigurationSerializable {

    private String name;
    private World world;
    private TeamLocation green, yellow, red, blue;
    private List<Location> diamonds;

    //TODO add bosses

    public Map(String name, TeamLocation red, TeamLocation green, TeamLocation yellow, TeamLocation blue, List<Location> diamonds) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.yellow = yellow;
        this.blue = blue;
        this.diamonds = diamonds;
    }

    public Map(String name) {
        this.name = name;
    }

    public Map(FileConfiguration config) throws Exception {
        this.name = config.getString("name");
        this.red = new TeamLocation(config.getConfigurationSection("team_red").getValues(false));
        this.yellow = new TeamLocation(config.getConfigurationSection("team_yellow").getValues(false));
        this.green = new TeamLocation(config.getConfigurationSection("team_green").getValues(false));
        this.blue = new TeamLocation(config.getConfigurationSection("team_blue").getValues(false));
        this.diamonds = ConfigUtil.deserializeLocationArray(config.getConfigurationSection("diamonds").getValues(false));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getDiamonds() {
        if (diamonds == null) {
            diamonds = new ArrayList<>();
        }
        return diamonds;
    }

    public void addDiamond(Location location) {
        if (!Utils.containsLocation(getDiamonds(), location)) {
            getDiamonds().add(location);
        }
    }

    public TeamLocation getTeamLocation(TeamColor color) {
        switch (color) {
            case RED:
                return getRedTeamLocation();
            case BLUE:
                return getBlueTeamLocation();
            case GREEN:
                return getGreenTeamLocation();
            case YELLOW:
                return getYellowTeamLocation();
            default:
                return getRedTeamLocation(); //????
        }
    }

    public TeamLocation getBlueTeamLocation() {
        if (blue == null) {
            blue = new TeamLocation(null, null, null, null, null);
        }
        return blue;
    }

    public TeamLocation getRedTeamLocation() {
        if (red == null) {
            red = new TeamLocation(null, null, null, null, null);
        }
        return red;
    }

    public TeamLocation getYellowTeamLocation() {
        if (yellow == null) {
            yellow = new TeamLocation(null, null, null, null, null);
        }
        return yellow;
    }

    public TeamLocation getGreenTeamLocation() {
        if (green == null) {
            green = new TeamLocation(null, null, null, null, null);
        }
        return green;
    }

    public World getWorld() {
        if (this.world == null) {
            loadWorld();
        }
        return this.world;
    }

    public boolean isWorldCopied() {
        return Annihilation.getInstance().getServer().getWorld(this.name) != null;
    }

    public boolean isWorldLoaded() {
        for (World world : Annihilation.getInstance().getServer().getWorlds()) {
            if (world.getName().equals(getName())) {
                return true;
            }
        }
        return false;
    }

    public void copyWorld() {
        File sourceFile = new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR, getName());
        File targetFile = new File(Annihilation.getInstance().getServer().getWorldContainer().getAbsolutePath(), getName());

        if (targetFile.isDirectory()) {
            if (targetFile.list().length > 0) {
                WorldUtil.deleteWorld(getName());
            }
        }

        WorldUtil.copyWorld(sourceFile, targetFile);
        if (!WorldUtil.loadWorld(getName())) {
            //TODO log failure to create world
            Annihilation.getInstance().getLogger().info("FAILURE TO LOAD WORLD!");
        }
    }

    public void loadWorld() {
        if (!isWorldLoaded()) {
            copyWorld();
        }
        this.world = Annihilation.getInstance().getServer().getWorld(getName());
    }

    public boolean saveWorld() {
        if (isWorldLoaded()) {
            this.world.save();
            WorldUtil.deleteWorld(new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR + File.separator + getName()));

            File sourceFile = new File(Annihilation.getInstance().getServer().getWorldContainer().getAbsolutePath(), getName());
            File targetFile = new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR + File.separator + getName());
            WorldUtil.copyWorld(sourceFile, targetFile);
            return true;
        }
        return false;
    }

    public MapErrorCode checkMap() {
        if (getName() == null || getName().isEmpty()) {
            return MapErrorCode.MAP_NAME_NULL;
        } else if (!getRedTeamLocation().check()) {
            return MapErrorCode.RED_LOCATION_ERROR;
        } else if (!getBlueTeamLocation().check()) {
            return MapErrorCode.BLUE_LOCATION_ERROR;
        } else if (!getYellowTeamLocation().check()) {
            return MapErrorCode.YELLOW_LOCATION_ERROR;
        } else if (!getGreenTeamLocation().check()) {
            return MapErrorCode.GREEN_LOCATION_ERROR;
        }
        return MapErrorCode.OK;
    }

    public boolean isValid() {
        return checkMap() == MapErrorCode.OK;
    }

    public boolean saveData() {
        File config = new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR + File.separator + this.name + File.separator + "game.yml");
        if (config == null) {
            return false;
        }
        config.mkdirs();
        if (config.exists()) {
            config.delete();
        }
        createConfig();
        return true;
    }

    private void createConfig() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(Annihilation.getInstance().getMapManager().getMapFile(this.getName()));
        if (config == null) {
            return;
        }

        config.addDefaults(serialize());
    }


    public boolean equals(Map other) {
        return name.equals(other);
    }

    public BaseComponent[] getWorldComponents() {
        return new ComponentBuilder("loaded=").color(ChatColor.WHITE).bold(true).append("" + this.isWorldLoaded()).color(this.isWorldLoaded() ? ChatColor.GREEN : ChatColor.RED).bold(false).create();
    }

    public BaseComponent[] getDiamondsComponents() {
        return new ComponentBuilder("diamond_list=").color(ChatColor.WHITE).bold(true).append(GameUtil.arrayToString(getDiamonds(), true)).color(ChatColor.GREEN).bold(false).create();
    }

    public java.util.Map<String, Object> serialize() {
        java.util.Map<String, Object> serialize = new HashMap<>();

        serialize.put("name", getName());
        serialize.put("team_red", getRedTeamLocation().serialize());
        serialize.put("team_blue", getBlueTeamLocation().serialize());
        serialize.put("team_green", getGreenTeamLocation().serialize());
        serialize.put("team_yellow", getYellowTeamLocation().serialize());
        serialize.put("diamonds", ConfigUtil.serializeLocationArray(getDiamonds()));

        return serialize;
    }
}
