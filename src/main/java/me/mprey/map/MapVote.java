package me.mprey.map;

import java.util.HashMap;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapVote {

    private HashMap<Map, Integer> voteMap;

    private Map[] mapArray;

    public MapVote(Map[] mapArray) {
        this.mapArray = mapArray;
        this.voteMap = new HashMap<>();
        populate();
    }

    public Map[] getMapArray() {
        return mapArray;
    }

    public HashMap<Map, Integer> getVoteMap() {
        return voteMap;
    }

    public void populate() {
        for (Map map : mapArray) {
            if (!voteMap.containsKey(map)) {
                voteMap.put(map, 0);
            }
        }
    }

    public void addVote(int index) {
        addVote(mapArray[index]);
    }

    public void addVote(Map m) {
        if (voteMap.containsKey(m)) {
            voteMap.put(m, voteMap.get(m) + 1);
        }
    }

    public Map getWinner() {
        Map winner = null;
        int votes = -1;
        for (Map map : voteMap.keySet()) {
            if (voteMap.containsKey(map)) {
                if (voteMap.get(map) >= votes) {
                    winner = map;
                    votes = voteMap.get(map);
                }
            }
        }
        return winner;
    }

}
