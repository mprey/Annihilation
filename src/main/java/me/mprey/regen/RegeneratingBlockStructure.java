package me.mprey.regen;

import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.Map;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class RegeneratingBlockStructure {

    private Material type, placeholder, reward;
    private long delay;
    private boolean naturalDrop;
    private Sound sound;
    private RegeneratingBlockEffect effect;
    private int exp, rewardInt;

    public RegeneratingBlockStructure(Map<String, Object> deserialize) {
        this.type = Material.getMaterial(((String) deserialize.get("type")).toUpperCase());
        this.reward = Material.getMaterial(((String) deserialize.get("reward")).toUpperCase());
        this.rewardInt = (int) deserialize.get("reward_amount");
        this.delay = ((int) deserialize.get("delay")) * 20L;
        this.naturalDrop = (boolean) deserialize.get("natural_drop");
        this.placeholder = Material.getMaterial(((String) deserialize.get("place_holder")).toUpperCase());
        this.exp = (int) deserialize.get("exp_reward");
        this.sound = Sound.valueOf(((String) deserialize.get("sound")).toUpperCase());
        this.effect = RegeneratingBlockEffect.valueOf(((String) deserialize.get("effect")).toUpperCase());
    }

    public Material getType() {
        return type;
    }

    public Material getReward() {
        return reward;
    }

    public int getRewardInt() {
        return rewardInt;
    }

    public Material getPlaceholder() {
        return placeholder;
    }

    public long getDelay() {
        return delay;
    }

    public boolean isNaturalDrop() {
        return naturalDrop;
    }

    public Sound getSound() {
        return sound;
    }

    public RegeneratingBlockEffect getEffect() {
        return effect;
    }

    public int getExp() {
        return exp;
    }

    public boolean isValid() {
        return getType() != null && getReward() != null && getRewardInt() >= 0 && getPlaceholder() != null && getDelay() >= 0 && getEffect() != null && getExp() >=0;
    }
}
