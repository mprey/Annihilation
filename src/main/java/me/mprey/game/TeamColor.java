package me.mprey.game;

import me.mprey.Annihilation;
import me.mprey.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;
import org.bukkit.scoreboard.Team;

/**
 * Created by Mason Prey on 2/6/16.
 */
public enum TeamColor {

    YELLOW(Color.fromRGB(255, 255, 85), ChatColor.YELLOW, DyeColor.YELLOW, (byte) 4),
    RED(Color.fromRGB(255, 85, 85), ChatColor.RED, DyeColor.RED, (byte) 14),
    BLUE(Color.fromRGB(85, 85, 255), ChatColor.BLUE, DyeColor.BLUE, (byte) 11),
    GREEN(Color.fromRGB(85, 255, 85), ChatColor.GREEN, DyeColor.GREEN, (byte) 13);

    private Color color;
    private ChatColor chatColor;
    private DyeColor dyeColor;
    private byte colorData;

    TeamColor(Color color, ChatColor chatColor, DyeColor dyeColor, byte colorData) {
        this.color = color;
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
        this.colorData = colorData;
    }

    public byte getColorData() {
        return  colorData;
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

    public ItemStack getBlock() {
        ItemStack is = new Wool(this.dyeColor).toItemStack();
        is.setAmount(1);
        return is;
    }

    public String toString() {
        return Annihilation._l("game." + this.name() + "_color");
    }

}
