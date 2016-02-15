package me.mprey.map;

import me.mprey.Annihilation;
import me.mprey.game.TeamLocation;
import me.mprey.stats.MapStatistics;
import me.mprey.util.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapManager {

    public static final String MAP_DIR = "maps", MAP_FILE = "map.yml";

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
                MapStatistics.getStatistics(map);
                Annihilation.getInstance().getLogger().info("loaded map " + map.getName() + "!"); //TODO locale
            }
        } catch (Exception e) {
            //TODO print out unable to load map mapName
            e.printStackTrace();
        }
    }

    public void loadMaps() {
        File mapsDirectory = new File(Annihilation.getInstance().getDataFolder(), MAP_DIR);
        if (!mapsDirectory.exists()) {
            try {
                mapsDirectory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    if (cfg.isFile() && cfg.getName().equals(MAP_FILE)) {
                        loadMap(cfg);
                    }
                }
            }
        } else {
            Annihilation.getInstance().getLogger().info("unable to find any maps!"); //TODO locale
        }
    }

    public boolean addMap(String map) {
        if (!isMap(map)) {
            mapList.add(new Map(map));
            return true;
        }
        return false;
    }

    public void removeMap(Map map) {
        //TODO check if map is being used
        mapList.remove(map);
        File mapDir = new File(Annihilation.getInstance().getDataFolder() + File.separator + MAP_DIR + File.separator + map.getName());
        if (mapDir.exists()) {
            mapDir.delete();
        }
    }

    public MapErrorCode saveMap(Map map) {
        if (map != null) {
            MapErrorCode code = map.checkMap();
            if (code == MapErrorCode.OK) {
                map.save();
                MapStatistics.getStatistics(map);
            } else {
                return code;
            }
        }
        return null;
    }

    public File getMapFile(Map map) {
        if (map != null) {
            return new File(Annihilation.getInstance().getDataFolder() + File.separator + MAP_DIR + File.separator + map.getName() + File.separator + MAP_FILE);
        }
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
