package me.mprey.game;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Nexus {

    private Block block;

    private int health;

    public Nexus(Block block, int health) {
        this.block = block;
        this.health = health;
    }

    public Block getBlock() {
        return this.block;
    }

    public int getHealth() {
        return health;
    }

    public void damage(int multiplier) {
        this.health -= 1 * multiplier;
    }

    public void die() {
        if (block != null) {
            block.setType(Material.BEDROCK);
        }
    }

    public void reset() {
        if (block != null) {
            block.setType(Material.ENDER_STONE);
        }
    }

}
