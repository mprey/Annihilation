package me.mprey.bosses;

import me.mprey.util.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/13/16.
 */
public class Boss implements ConfigurationSerializable {

    private String name;
    private Location spawn;
    private int health;
    private BossType bossType;
    private List<ItemStack> rewards;
    private Location rewardLoc;

    public Boss(String name, int health, BossType type, Location spawn, Location rewardLoc, List<ItemStack> rewards) {
        this.name = name;
        this.health = health;
        this.bossType = type;
        this.spawn = spawn;
        this.rewardLoc = rewardLoc;
        this.rewards = rewards;
    }

    public Boss(Map<String, Object> deserialize) {
        this.name = (String) deserialize.get("name");
        this.spawn = ConfigUtil.deserializeLocation(deserialize.get("spawn"));
        this.health = (int) deserialize.get("health");
        this.bossType = BossType.valueOf((String) deserialize.get("boss_type"));
        this.rewardLoc = ConfigUtil.deserializeLocation(deserialize.get("reward_loc"));
        //this.rewards = ConfigUtil.deserializeItemStackArray((Map<String, Object) deserialize.get("rewards"));
    }

    public Boss(Object object) {
        this((Map<String, Object>) object);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public BossType getBossType() {
        return bossType;
    }

    public void setBossType(BossType bossType) {
        this.bossType = bossType;
    }

    public List<ItemStack> getRewards() {
        return rewards;
    }

    public void setRewards(List<ItemStack> rewards) {
        this.rewards = rewards;
    }

    public Location getRewardLoc() {
        return rewardLoc;
    }

    public void setRewardLoc(Location rewardLoc) {
        this.rewardLoc = rewardLoc;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> serialize = new HashMap<>();

        serialize.put("name", name);
        serialize.put("spawn", ConfigUtil.serializeLocation(spawn));
        serialize.put("health", health);
        serialize.put("boss_type", bossType.toString());
        serialize.put("reward_loc", ConfigUtil.serializeLocation(rewardLoc));
        //serialize.put("rewards", ConfigUtil.deserializeItemStackArray(rewards)); TODO

        return serialize;
    }

}
