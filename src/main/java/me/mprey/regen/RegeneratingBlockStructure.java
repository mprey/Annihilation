package me.mprey.regen;

import org.bukkit.Material;
import org.bukkit.Sound;

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

    public RegeneratingBlockStructure(Material type, Material reward, int rewardInt, long delay, boolean naturalDrop, Material placeholder, int exp, Sound sound, RegeneratingBlockEffect effect) {
        this.type = type;
        this.reward = reward;
        this.rewardInt = rewardInt;
        this.delay = delay;
        this.naturalDrop = naturalDrop;
        this.exp = exp;
        this.placeholder = placeholder;
        this.sound = sound;
        this.effect = effect;
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
}
