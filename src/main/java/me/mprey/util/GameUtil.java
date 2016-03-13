package me.mprey.util;

import me.mprey.regen.RegeneratingBlockEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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

    public static void dropItem(Location location, Material material, int amount) {
        location.getWorld().dropItem(location, new ItemStack(material, amount));
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

    public static String locationToString(Location loc) {
        if (loc == null) {
            return "null";
        } else {
            return "{world=" + loc.getWorld().getName() + ", x=" + loc.getBlockX() + ", y=" + loc.getBlockY() +", z=" + loc.getBlockZ() + "}";
        }
    }

    public static String helloItsMeIveBeenWonderingIfAfterAllTheseYearsYouWouldLikeToMeet(List<String> array) {
        String string = "{";
        for (int i = 0; i < array.size(); i++) {
            string += array.get(i);
            if (i != array.size() - 1) {
                string += ", ";
            }
        }
        return string + "}";
    }

    public static String arrayToString(List<Location> array) {
        List<String> newArray = new ArrayList<>();
        for (Location loc : array) {
            newArray.add(locationToString(loc));
        }
        return helloItsMeIveBeenWonderingIfAfterAllTheseYearsYouWouldLikeToMeet(newArray);
    }

    public static String arrayToString(List<Location> array, boolean appendLine) {
        List<String> newArray = new ArrayList<>();
        for (Location loc : array) {
            newArray.add(locationToString(loc));
        }
        return helloItsMeIveBeenWonderingIfAfterAllTheseYearsYouWouldLikeToMeet(newArray);
    }

}
