package me.mprey.util;

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

}
