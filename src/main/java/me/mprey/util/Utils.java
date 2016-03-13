package me.mprey.util;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

    public static boolean containsLocation(List<Location> locationList, Location input) {
        for (Location location : locationList) {
            if (location.getBlockX() == input.getBlockX() && location.getBlockY() == input.getBlockY() && location.getBlockZ() == input.getBlockZ() && location.getWorld().getName().equals(input.getWorld().getName())) {
                return true;
            }
        }
        return false;
    }

    public static int[] interpretRange(String input) {
        if (input.contains(DELIMITER)) {
            String[] split = input.split(DELIMITER);
            int min = Integer.parseInt(split[0]);
            int max = Integer.parseInt(split[1]);
            int[] array = new int[max - min + 1];
            int x = 0;
            for (int i = min; i <= max; i++) {
                array[x] = i;
                x++;
            }
            return array;
        } else {
            return new int[] {Integer.parseInt(input)};
        }
    }

    public static String capitalize(String input) {
        return WordUtils.capitalizeFully(input);
    }


}
