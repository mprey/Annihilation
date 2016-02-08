package me.mprey.util;

import me.mprey.regen.RegeneratingBlockEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class GameUtil {

    public static void playEffect(RegeneratingBlockEffect effect, Location location) {
        switch (effect) {

            case GRAVEL:
                playGravel(location);
                break;

            case NONE:
                break;

            default:
                break;
        }
    }

    public static void dropItem(Location location, Material material, int amount, boolean naturally) {
        if (!naturally) location.getWorld().dropItem(location, new ItemStack(material, amount));
        else location.getWorld().dropItemNaturally(location, new ItemStack(material, amount));
    }

    private static void playGravel(Location location) {
        int boneAmount = Utils.randomInt(0, 5);
        int featherAmount = Utils.randomInt(0, 5);
        int stickAmount = Utils.randomInt(0, 3);

        World world = location.getWorld();

        world.dropItem(location, new ItemStack(Material.BONE, boneAmount));
        world.dropItem(location, new ItemStack(Material.FEATHER, featherAmount));
        world.dropItem(location, new ItemStack(Material.STICK, stickAmount));
    }

}
