package me.mprey.map;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class MapVote {

    private HashMap<Map, Integer> voteMap;

    private List<UUID> voters;

    private Map[] mapArray;

    public MapVote(Map[] mapArray) {
        this.mapArray = mapArray;
        this.voteMap = new HashMap<>();
        this.voters = new ArrayList<>();
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

    public void addVote(Player player, int index) {
        addVote(player, mapArray[index]);
    }

    public boolean addVote(Player player, Map m) {
        if (!voters.contains(player.getUniqueId()) && voteMap.containsKey(m)) {
            voteMap.put(m, voteMap.get(m) + 1);
            voters.add(player.getUniqueId());
        }
        return false;
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
