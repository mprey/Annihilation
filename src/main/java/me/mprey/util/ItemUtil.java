package me.mprey.util;

import me.mprey.game.TeamColor;
import me.mprey.map.Map;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class ItemUtil {

    public static ItemStack nameAndLore(ItemStack itemStack, String name, List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public static ItemStack nameAndLore(Material itemStack, String name, List<String> lore) {
        return nameAndLore(new ItemStack(itemStack, 1), name, lore);
    }

    public static ItemStack addData(ItemStack item, java.util.Map<String, String> data) {
        List<String> lore = new ArrayList<>();
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            lore.addAll(item.getItemMeta().getLore());
        }
        ItemMeta n = item.getItemMeta();
        lore.add("");
        lore.add("");
        lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "DATA:");
        for (String key : data.keySet()) {
            lore.add(ChatColor.RESET + "" + ChatColor.WHITE + key + "=" + data.get(key));
        }
        n.setLore(lore);
        item.setItemMeta(n);
        return item;
    }

    public static String getData(ItemStack item, String key) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            int dataIndex = -1;
            for (int i = 0; i < lore.size(); i++) {
                String line = lore.get(i);
                if (line != null && line.contains("DATA:")) {
                    dataIndex = i;
                    break;
                }
            }
            if (dataIndex > 0) {
                int startIndex = dataIndex + 1;
                for (int i = startIndex; i < lore.size(); i++) {
                    String line = ChatColor.stripColor(lore.get(i));
                    if (line.contains("=")) {
                        String[] parts = line.split("=");
                        if (parts[0].equals(key)) {
                            return parts[1];
                        }
                    }
                }
            }
        }
        return null;
    }

    public static List<BaseComponent> getMapLore(Map map) {
        List<BaseComponent> lore = new ArrayList<>();

        TextComponent worldText = new TextComponent(ChatColor.WHITE + "World information");
        worldText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, map.getWorldComponents()));
        lore.add(worldText);

        TextComponent diamondsText = new TextComponent(ChatColor.BLUE + "Diamonds information");
        diamondsText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, map.getDiamondsComponents()));
        lore.add(diamondsText);

        TextComponent redText = new TextComponent(ChatColor.RED + TeamColor.RED.toString() + " team information");
        redText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, map.getRedTeamLocation().getComponents()));
        lore.add(redText);

        TextComponent greenText = new TextComponent(ChatColor.GREEN + TeamColor.GREEN.toString() + " team information");
        greenText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, map.getGreenTeamLocation().getComponents()));
        lore.add(greenText);

        TextComponent blueText = new TextComponent(ChatColor.BLUE + TeamColor.BLUE.toString() + " team information");
        blueText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, map.getBlueTeamLocation().getComponents()));
        lore.add(blueText);

        TextComponent yellowText = new TextComponent(ChatColor.YELLOW + TeamColor.YELLOW.toString() + " team information");
        yellowText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, map.getYellowTeamLocation().getComponents()));
        lore.add(yellowText);

        return lore;
    }

}
