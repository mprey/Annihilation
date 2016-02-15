package me.mprey.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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

    public static int interpretRange(String input) {
        String[] split = input.split(DELIMITER);

        if (split != null && split.length == 2) {
            if (isInteger(split[0]) && isInteger(split[1])) {
                return Utils.randomInt(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            }
        }

        return 0;
    }

    public static String[] getResourceListing(Class<?> clazz, String path)
            throws UnsupportedEncodingException, URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
			/* A file path: easy enough */
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
			/*
			 * In case of a jar file, we can't actually find a directory. Have
			 * to assume the same jar as clazz.
			 */
            String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
			/* A JAR path */
            String jarPath = dirURL.getPath().substring(5,
                    dirURL.getPath().indexOf("!")); // strip out only the JAR
            // file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); // gives ALL entries
            // in jar
            Set<String> result = new HashSet<String>(); // avoid duplicates in
            // case it is a
            // subdirectory
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) { // filter according to the path
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        // if it is a subdirectory, we just return the directory
                        // name
                        entry = entry.substring(0, checkSubdir);
                    }
                    result.add(entry);
                }
            }
            return result.toArray(new String[result.size()]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL "
                + dirURL);
    }

}
