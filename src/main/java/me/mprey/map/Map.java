package me.mprey.map;

import me.mprey.Annihilation;
import me.mprey.game.TeamLocation;
import me.mprey.util.ConfigUtil;
import org.bukkit.Location;
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
    private Region mapArea;
    private TeamLocation green, yellow, red, blue;
    private Location lobby;
    private List<Location> diamonds;

    public Map(String name, Region mapArea, Location lobby, TeamLocation red, TeamLocation green, TeamLocation yellow, TeamLocation blue, List<Location> diamonds) {
        this.name = name;
        this.lobby = lobby;
        this.red = red;
        this.mapArea = mapArea;
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
        this.lobby = ConfigUtil.deserializeLocation(config.get("lobby"));
        this.mapArea = new Region(config.getConfigurationSection("region").getValues(false));
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
        getDiamonds().add(location);
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

    public Region getMapArea() {
        if (mapArea == null) {
            mapArea = new Region(null, null);
        }
        return mapArea;
    }

    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location location) {
        this.lobby = location;
    }

    public MapErrorCode checkMap() {
        if (getName() == null || getName().isEmpty()) {
            return MapErrorCode.MAP_NAME_NULL;
        } else if (!getMapArea().isValid()) {
            return MapErrorCode.MAP_REGION_ERROR;
        } else if (getLobby() == null) {
            return MapErrorCode.MAP_LOBBY_NULL;
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

    public void save() {
        File config = new File(Annihilation.getInstance().getDataFolder() + File.separator + MapManager.MAP_DIR + File.separator + this.name + File.separator + "game.yml");
        if (config == null) {
            return;
        }
        config.mkdirs();
        if (config.exists()) {
            config.delete();
        }
        createConfig();
    }

    private void createConfig() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(Annihilation.getInstance().getMapManager().getMapFile(this));
        if (config == null) {
            return;
        }

        config.addDefaults(serialize());
    }


    public boolean equals(Map other) {
        return name.equals(other);
    }

    public java.util.Map<String, Object> serialize() {
        java.util.Map<String, Object> serialize = new HashMap<>();

        serialize.put("name", getName());
        serialize.put("region", getMapArea().serialize());
        serialize.put("lobby", ConfigUtil.serializeLocation(getLobby()));
        serialize.put("team_red", getRedTeamLocation().serialize());
        serialize.put("team_blue", getBlueTeamLocation().serialize());
        serialize.put("team_green", getGreenTeamLocation().serialize());
        serialize.put("team_yellow", getYellowTeamLocation().serialize());
        serialize.put("diamonds", ConfigUtil.serializeLocationArray(getDiamonds()));

        return serialize;
    }
}
