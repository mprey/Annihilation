package me.mprey.util;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static ItemStack addData(ItemStack item, Map<String, String> data) {
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

}
