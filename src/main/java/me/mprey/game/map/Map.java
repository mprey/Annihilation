package me.mprey.game.map;

import me.mprey.game.TeamLocation;
import org.bukkit.Location;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Map {

    private String name;
    private Region mapArea;
    private TeamLocation green, yellow, red, blue;
    private Location[] diamonds, bosses;

    public Map(String name, Region mapArea, TeamLocation red, TeamLocation green, TeamLocation yellow, TeamLocation blue, Location[] diamonds, Location[] bosses) {
        this.name = name;
        this.red = red;
        this.mapArea = mapArea;
        this.green = green;
        this.yellow = yellow;
        this.blue = blue;
        this.bosses = bosses;
        this.diamonds = diamonds;
    }

    public String getName() {
        return name;
    }

    public Location[] getBosses() {
        return bosses;
    }

    public Location[] getDiamonds() {
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
}
