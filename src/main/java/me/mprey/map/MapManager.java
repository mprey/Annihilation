package me.mprey.map;

import me.mprey.Annihilation;
import me.mprey.game.TeamLocation;
import me.mprey.util.ConfigUtil;
import org.bukkit.Location;
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

    public static final String MAP_DIR = "maps";

    private ArrayList<Map> mapList;

    public MapManager() {
        this.mapList = new ArrayList<>();
    }

    private void loadMap(File configFile) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        try {
            Map map = new Map(config);
            if (map != null) {
                mapList.add(map);
            }
        } catch (Exception e) {
            //TODO print out unable to load map mapName
            e.printStackTrace();
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

    public boolean addMap(String map) {
        if (!isMap(map)) {
            mapList.add(new Map(map));
        }
        return true;
    }

    public void removeMap() {
        //TODO
    }

    public MapErrorCode saveMap(Map map) {
        //TODO
        return null;
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

    public ArrayList<Map> getValidMaps() {
        ArrayList<Map> validMaps = new ArrayList<>();
        for (Map m : getMapList()) {
            if (m.checkMap() == MapErrorCode.OK) {
                validMaps.add(m);
            }
        }
        return validMaps;
    }

    public Map[] randomMapArray(int amount) {
        ArrayList<Map> valid = getValidMaps();
        if (amount > 0 && valid.size() != 0) {
            if (valid.size() <= amount) {
                return valid.toArray(new Map[0]);
            }
            List<Map> list = new ArrayList<>(valid.size());
            for (Map m : valid) {
                if (m.checkMap() == MapErrorCode.OK) {
                    list.add(m);
                }
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
