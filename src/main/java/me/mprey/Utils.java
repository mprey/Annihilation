package me.mprey;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class Utils {

    public static void dropItem(Location location, Material material, int amount) {
        location.getWorld().dropItem(location, new ItemStack(material, amount));
    }

}
