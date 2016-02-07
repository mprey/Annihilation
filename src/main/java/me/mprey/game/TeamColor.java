package me.mprey.game;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.scoreboard.Team;

/**
 * Created by Mason Prey on 2/6/16.
 */
public enum TeamColor {

    YELLOW(Color.fromRGB(255, 255, 85), ChatColor.YELLOW, DyeColor.YELLOW),
    RED(Color.fromRGB(255, 85, 85), ChatColor.RED, DyeColor.RED),
    BLUE(Color.fromRGB(85, 85, 255), ChatColor.BLUE, DyeColor.LIGHT_BLUE),
    GREEN(Color.fromRGB(85, 255, 85), ChatColor.GREEN, DyeColor.LIME);

    private Color color;
    private ChatColor chatColor;
    private DyeColor dyeColor;

    private TeamColor(Color color, ChatColor chatColor, DyeColor dyeColor) {
        this.color = color;
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
    }

    public Color getColor() {
        return color;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

}
