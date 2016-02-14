package me.mprey.map;

import me.mprey.game.TeamLocation;
import me.mprey.util.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Map {

    private String name;
    private Region mapArea;
    private TeamLocation green, yellow, red, blue;
    private Location lobby;
    private List<Location> diamonds, bosses;

    public Map(String name, Region mapArea, Location lobby, TeamLocation red, TeamLocation green, TeamLocation yellow, TeamLocation blue, List<Location> diamonds, List<Location> bosses) {
        this.name = name;
        this.lobby = lobby;
        this.red = red;
        this.mapArea = mapArea;
        this.green = green;
        this.yellow = yellow;
        this.blue = blue;
        this.bosses = bosses;
        this.diamonds = diamonds;
    }

    public Map(String name) {
        this.name = name;
    }

    public Map(FileConfiguration config) throws Exception {
        this.name = config.getString("name");
        this.lobby = ConfigUtil.deserializeLocation(config.get("lobby"));
        this.mapArea = new Region(config.getConfigurationSection("region").getKeys(false));
        this.red = new TeamLocation(config.getConfigurationSection("team_red").getValues(false));
        this.yellow = new TeamLocation(config.getConfigurationSection("team_yellow").getValues(false));
        this.green = new TeamLocation(config.getConfigurationSection("team_green").getValues(false));
        this.blue = new TeamLocation(config.getConfigurationSection("team_blue").getValues(false));
        this.diamonds = ConfigUtil.deserializeLocationArray(config.getConfigurationSection("diamonds").getValues(false));
        this.bosses = ConfigUtil.deserializeLocationArray(config.getConfigurationSection("bosses").getValues(false));
    }

    public String getName() {
        return name;
    }

    public List<Location> getBosses() {
        if (bosses == null ) {
            bosses = new ArrayList<>();
        }
        return bosses;
    }

    public List<Location> getDiamonds() {
        return diamonds;
    }

    public TeamLocation getBlueTeamLocation() {
        return blue;
    }

    public TeamLocation getRedTeamLocation() {
        return red;
    }

    public TeamLocation getYellowTeamLocation() {
        return yellow;
    }

    public TeamLocation getGreenTeamLocation() {
        return green;
    }

    public Region getMapArea() {
        return mapArea;
    }

    public Location getLobby() {
        return lobby;
    }

    public MapErrorCode checkMap() {
        if (name == null || name.isEmpty()) {
            return MapErrorCode.MAP_NAME_NULL;
        } else if (mapArea == null || !mapArea.isValid()) {
            return MapErrorCode.MAP_REGION_NULL;
        } else if (lobby == null) {
            return MapErrorCode.MAP_LOBBY_NULL;
        } else if (red == null || !red.check()) {
            return MapErrorCode.RED_LOCATION_ERROR;
        } else if (blue == null || !blue.check()) {
            return MapErrorCode.BLUE_LOCATION_ERROR;
        } else if (yellow == null || !yellow.check()) {
            return MapErrorCode.YELLOW_LOCATION_ERROR;
        } else if (green == null || !green.check()) {
            return MapErrorCode.GREEN_LOCATION_ERROR;
        }
        return MapErrorCode.OK;
    }

    public boolean equals(Map other) {
        return name.equals(other);
    }
}
