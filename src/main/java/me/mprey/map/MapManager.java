package me.mprey.map;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.game.TeamLocation;
import me.mprey.stats.MapStatistics;
import me.mprey.util.ConfigUtil;
import me.mprey.util.Utils;
import me.mprey.util.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

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

    public static final String MAP_DIR = "maps", MAP_FILE = "map.yml", LOBBY_FILE = "lobby.yml";

    private ArrayList<Map> mapList;

    private Lobby lobby;

    public MapManager() {
        this.mapList = new ArrayList<>();
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    private void loadMap(String mapName) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(getMapFile(mapName));
        try {
            Map map = new Map(config);
            if (map != null) {
                mapList.add(map);
                MapStatistics.getStatistics(map);
                Annihilation.getInstance().getLogger().info(Annihilation._l("success.map.loaded", ImmutableMap.of("map", mapName)));
            }
        } catch (Exception e) {
            Annihilation.getInstance().getLogger().info(Annihilation._l("errors.map.unable_to_load", ImmutableMap.of("map", mapName)));
            e.printStackTrace();
        }
    }

    private void loadRawMap(String mapName) {
        try {
            Map map = new Map(mapName);
            if (map != null) {
                mapList.add(map);
                MapStatistics.getStatistics(map);
                Annihilation.getInstance().getLogger().info(Annihilation._l("success.map.loaded", ImmutableMap.of("map", mapName)));
            }
        } catch (Exception e) {
            Annihilation.getInstance().getLogger().info(Annihilation._l("errors.map.unable_to_load", ImmutableMap.of("map", mapName)));
            e.printStackTrace();
        }
    }

    private boolean loadLobby(File directory) {
        File lobbyFile = new File(directory, LOBBY_FILE);
        if (lobbyFile != null && lobbyFile.exists()) {
            this.lobby = new Lobby(YamlConfiguration.loadConfiguration(lobbyFile));
            return true;
        }
        return false;
    }

    public boolean lobbyMapExists() {
        return getMapDirectory("lobby").exists();
    }

    public boolean createLobbyFromFile() {
        this.lobby = new Lobby();
        this.lobby.getWorld();
        return this.lobby.isWorldLoaded();
    }

    public void createBlankLobby() {
        this.lobby = new Lobby(WorldUtil.createEmptyWorld("lobby"));
    }

    public void loadMaps() {
        File mapsDirectory = new File(Annihilation.getInstance().getDataFolder() + File.separator + MAP_DIR);
        if (!mapsDirectory.exists()) {
            mapsDirectory.mkdir();
        }
        File[] subFiles = mapsDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });
        if (subFiles.length > 0) {
            for (File mapDirectory : subFiles) {
                if (isMapDir(mapDirectory)) {
                    loadMap(mapDirectory.getName());
                } else if (isLobbyDir(mapDirectory)) {
                    loadLobby(mapDirectory);
                }
            }
        } else {
            Annihilation.getInstance().getLogger().info(Annihilation._l("errors.map.no_maps"));
        }
    }

    public boolean lobbyLoaded() {
        return this.lobby != null;
    }

    public boolean addMap(String map) {
        if (!isMap(map)) {
            loadRawMap(map);
            return true;
        }
        return false;
    }

    public void removeMap(Map map) {
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

            }
            return code;
        }
        return null;
    }

    public File getMapDirectory(String map) {
        return new File(Annihilation.getInstance().getDataFolder() + File.separator + MAP_DIR + File.separator + map);
    }

    public File getMapFile(String map) {
        return new File(getMapDirectory(map), MAP_FILE);
    }

    public Map getMap(String name) {
        for (Map map : mapList) {
            if (map.getName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }

    public List<String> getAvailableMaps() {
        File mapDir = new File(Annihilation.getInstance().getDataFolder() + File.separator + MAP_DIR);
        File[] sub = mapDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() && !pathname.getName().contains("lobby");
            }
        });
        List<String> available = new ArrayList<>();
        for (File subDirectory : sub) {
            if (!isMapDir(subDirectory) && !isMap(subDirectory.getName())) {
                available.add(subDirectory.getName());
            }
        }
        return available;
    }

    private boolean isMapDir(File file) {
        if (file.exists() && file.isDirectory()) {
            for (File cfg : file.listFiles()) {
                if (cfg.isFile() && cfg.getName().equals(MAP_FILE)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLobbyDir(File file) {
        if (file.exists() && file.isDirectory()) {
            for (File cfg : file.listFiles()) {
                if (cfg.isFile() && cfg.getName().equals(LOBBY_FILE)) {
                    return true;
                }
            }
        }
        return false;
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
