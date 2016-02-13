package me.mprey.map;

import me.mprey.Annihilation;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapManager {

    public static final String MAP_DIR = "games";

    private ArrayList<Map> mapList;

    public MapManager() {
        this.mapList = new ArrayList<>();
    }

    private void loadMap(File configFile) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        String mapName = config.getString("name");
        if (mapName == null || mapName.isEmpty()) {
            return;
        }

    }

    public void loadMaps() {
        File mapsDirectory = new File(Annihilation.getInstance().getDataFolder(), MAP_DIR);
        if (!mapsDirectory.exists()) {
            //TODO log unable to find map directory
            return;
        }
        File[] subFiles = mapsDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        if (subFiles.length > 0) {
            for (File mapDirectory : subFiles) {
                File[] sub = mapDirectory.listFiles();
                for (File cfg : sub) {
                    if (cfg.isFile() && cfg.getName().equals("game.yml")) {
                        loadMap(cfg);
                    }
                }
            }
        } else {
            //TODO log unable to find any maps
        }
    }

    public void addMap() {

    }

    public void removeMap() {

    }

    public Map getMap(String name) {
        for (Map map : mapList) {
            if (map.getName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }

    public boolean isMap(String name) {
        return getMap(name) != null;
    }

    public ArrayList<Map> getMapList() {
        return mapList;
    }

    public Map[] randomMapArray(int amount) {
        if (amount > 0 && mapList.size() != 0) {
            if (mapList.size() <= amount) {
                return mapList.toArray(new Map[0]);
            }
            List<Map> list = new ArrayList<>(mapList.size());
            for (Map m : mapList) {
                list.add(m);
            }
            Collections.shuffle(list);

            Map[] randomMaps = new Map[amount];
            for (int i = 0; i < amount; i++) {
                randomMaps[i] = list.get(i);
            }
            return randomMaps;
        }
        return null;
    }

}
