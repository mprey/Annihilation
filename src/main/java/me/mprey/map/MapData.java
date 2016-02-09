package me.mprey.map;

import me.mprey.game.TeamLocation;
import me.mprey.stats.MapStatistics;
import org.bukkit.Location;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapData {

    private TeamLocation red, blue, green, yellow;
    private Location[] diamonds, bosses;

    public MapData(TeamLocation red, TeamLocation blue, TeamLocation green, TeamLocation yellow, Location[] diamonds, Location[] bosses, MapStatistics stats) {
        this.red = red;
        this.blue = blue;
        this.green = green;
        this.yellow = yellow;
        this.diamonds = diamonds;
        this.bosses = bosses;
    }

}
