package me.mprey.stats;

import me.mprey.Annihilation;
import me.mprey.gui.IconGUI;
import me.mprey.util.IconMenu;
import org.bukkit.entity.Player;

/**
 * Created by Mason Prey on 2/25/16.
 */
public class StatsGUI extends IconGUI {

    private Statistics stats;

    private boolean isMap;

    public StatsGUI(Player player, Statistics stats) {
        super(player);
        this.isMap = stats instanceof MapStatistics;
    }

    public IconMenu createMenu() {
        return isMap ? getMapStats() : getPlayerStats();
    }

    public IconMenu getMapStats() {
        return null;
    }

    public IconMenu getPlayerStats() {
        return null;
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        String key = isMap ? "gui.stats.map.inventory_name" : "gui.stats.player.inventory_name";
        return Annihilation._l(key);
    }
}
