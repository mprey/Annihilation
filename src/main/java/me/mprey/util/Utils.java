package me.mprey.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class Utils {

    public static final String DELIMITER = "-";

    public static int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static int interpretRange(String input) {
        String[] split = input.split(DELIMITER);

        if (split != null && split.length == 2) {
            if (isInteger(split[0]) && isInteger(split[1])) {
                return Utils.randomInt(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
        }

        return 0;
    }

}
