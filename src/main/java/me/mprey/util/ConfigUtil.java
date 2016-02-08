package me.mprey.util;

import me.mprey.Annihilation;
import me.mprey.regen.RegeneratingBlock;
import me.mprey.regen.RegeneratingBlockEffect;
import me.mprey.regen.RegeneratingBlockStructure;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class ConfigUtil {

    public static ArrayList<RegeneratingBlockStructure> getRegeneratingBlockStructures(FileConfiguration config, String path) {
        ArrayList<RegeneratingBlockStructure> structureList = new ArrayList<>();
        if (config != null && path != null && config.isConfigurationSection(path)) {
            for (String key : config.getConfigurationSection(path).getKeys(false)) {
                try {
                    RegeneratingBlockStructure structure = new RegeneratingBlockStructure(
                            getMaterial(config, path + ".type"),
                            getMaterial(config, path + ".reward"),
                            config.getInt(path + ".reward_amount"),
                            config.getInt(path + ".delay") * 20L,
                            config.getBoolean(path + ".natural_drop"),      //wtf is going on O_O :DDD its 1am
                            getMaterial(config, path + ".place_holder"),
                            config.getInt(path + ".exp_reward"),
                            getSound(config, path + ".sound"),
                            getRegeneratingBlockEffect(config, path + ".effect"));
                    if (structure != null && structure.isValid()) {
                        structureList.add(structure);
                    }
                } catch (Exception e) { //bad programming?
                    Annihilation.getInstance().getLogger().log(Level.SEVERE, "TODO"); //TODO replace with lang file
                    e.printStackTrace();
                }
            }
        }
        return structureList;
    }

    public static Sound getSound(FileConfiguration config, String path) {
        if (config != null && config.isConfigurationSection(path)) {
            return Sound.valueOf(config.getString(path).toUpperCase());
        }
        return null;
    }

    public static RegeneratingBlockEffect getRegeneratingBlockEffect(FileConfiguration config, String path) {
        if (config != null && config.isConfigurationSection(path)) {
            return RegeneratingBlockEffect.valueOf(config.getString(path).toUpperCase());
        }
        return null;
    }

    public static Material getMaterial(FileConfiguration config, String path) {
        if (config != null && config.isConfigurationSection(path)) {
            return Material.getMaterial(config.getString(path).toUpperCase());
        }
        return null;
    }

    public static Map<String, Object> serializeLocation(Location location) {
        Map<String, Object> section = new HashMap<String, Object>();

        section.put("x", location.getX());
        section.put("y", location.getY());
        section.put("z", location.getZ());
        section.put("pitch", (double) location.getPitch());
        section.put("yaw", (double) location.getYaw());
        section.put("world", location.getWorld().getName());

        return section;
    }

    public static Location deserializeLocation(Object obj) {
        if (obj instanceof Location) {
            return (Location) obj;
        }
        Map<String, Object> section = new HashMap<String, Object>();
        if (obj instanceof MemorySection) {
            MemorySection sec = (MemorySection) obj;
            for (String key : sec.getKeys(false)) {
                section.put(key, sec.get(key));
            }
        } else if (obj instanceof ConfigurationSection) {
            ConfigurationSection sec = (ConfigurationSection) obj;
            for (String key : sec.getKeys(false)) {
                section.put(key, sec.get(key));
            }
        } else {
            section = (Map<String, Object>) obj;
        }

        try {
            if (section == null) {
                return null;
            }

            double x = Double.valueOf(section.get("x").toString());
            double y = Double.valueOf(section.get("y").toString());
            double z = Double.valueOf(section.get("z").toString());
            float yaw = Float.valueOf(section.get("yaw").toString());
            float pitch = Float.valueOf(section.get("pitch").toString());
            World world = Bukkit.getServer().getWorld(section.get("world").toString());

            if (world == null) {
                return null;
            }

            return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception ex) {
            //TODO log error of location
            ex.printStackTrace();
        }

        return null;
    }

}