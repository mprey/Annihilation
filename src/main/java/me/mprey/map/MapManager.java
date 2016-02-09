package me.mprey.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapManager {

    private ArrayList<Map> mapList;

    public MapManager() {
        this.mapList = new ArrayList<>();
    }

    public void loadMaps() {

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
