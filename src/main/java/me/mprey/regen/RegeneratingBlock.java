package me.mprey.regen;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class RegeneratingBlock {

    private Block block;
    private Material placeholder, original;
    private long time;

    public RegeneratingBlock(Block block, Material original, Material placeholder, long time) {
        this.block = block;
        this.original = original;
        this.placeholder = placeholder;
        this.time = time;
        block.setType(placeholder);
    }

    public Block getBlock() {
        return block;
    }

    public Material getOriginal() {
        return original;
    }

    public Material getPlaceholder() {
        return placeholder;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void replace() {
        block.setType(original);
    }

}
