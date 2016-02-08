package me.mprey.regen;

import me.mprey.Annihilation;
import me.mprey.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Created by Mason Prey on 2/7/16.
 */
public class RegeneratingBlockManager {

    private static long WAIT_TIME = 0L;
    private static long INTERVAL_TIME = 10L;

    private List<RegeneratingBlock> blockList;

    private BukkitTask runnable;

    private ArrayList<RegeneratingBlockStructure> structures;

    public RegeneratingBlockManager(ArrayList<RegeneratingBlockStructure> structures, long interval) {
        this.blockList = new ArrayList<>();
        this.structures = structures;
        if (interval > 0) {
            INTERVAL_TIME = interval;
        }
        start();
    }

    public void start() {
        runnable = Bukkit.getScheduler().runTaskTimer(Annihilation.getInstance(), new Runnable() {

            public void run() {
                for (Object object : blockList.toArray()) {
                    RegeneratingBlock block = (RegeneratingBlock) object; //avoid ConcurrentModificationException
                    if (block.getTime() <= 0) {
                        block.replace();
                        removeBlock(block);
                    } else block.setTime(block.getTime() - INTERVAL_TIME);
                }
            }

        }, WAIT_TIME, INTERVAL_TIME);
    }

    public void addBlock(RegeneratingBlock block) {
        this.blockList.add(block);
    }

    public void removeBlock(RegeneratingBlock block) {
        this.blockList.remove(block);
    }

    public List<RegeneratingBlock> getBlockList() {
        return this.blockList;
    }

    public void handleBlockBreak(BlockBreakEvent event) {
        //TODO check if block is within game, check if game running
        if (isRegenerating(event.getBlock())) {
            event.setCancelled(true);
            return;
        }
        for (RegeneratingBlockStructure structure : structures) {
            if (structure.getType() == event.getBlock().getType()) {
                addBlock(new RegeneratingBlock(event.getBlock(), event.getBlock().getType(), structure.getPlaceholder(), structure.getDelay()));
                event.setCancelled(true);
                if (structure.isNaturalDrop()) {
                    Utils.dropItem(event.getBlock().getLocation(), structure.getReward(), structure.getRewardInt());
                } else event.getPlayer().getInventory().addItem(new ItemStack(structure.getReward(), structure.getRewardInt()));
                event.getPlayer().giveExp(structure.getExp());
                event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), structure.getSound(), 1, 1);
                return;
            }
        }
    }

    public boolean handleInteract(PlayerInteractEvent event) {
        Block clicked = event.getClickedBlock();
        if (isRegenerating(clicked)) {
            RegeneratingBlock block = getRegeneratingBlock(clicked);
            double seconds = (block.getTime() / 20.00);
            event.getPlayer().sendMessage("This block regenerates in " + seconds + " seconds.");
            return true;
        }
        return false;
    }

    public RegeneratingBlock getRegeneratingBlock(Block block) {
        for (RegeneratingBlock regeneratingBlock : blockList) {
            if (regeneratingBlock.getBlock().getLocation().equals(block.getLocation())) {
                return regeneratingBlock;
            }
        }
        return null;
    }

    public boolean isRegenerating(Block block) {
        for (RegeneratingBlock regeneratingBlock : blockList) {
            if (regeneratingBlock.getBlock().getLocation().equals(block.getLocation())) {
                return true;
            }
        }
        return false;
    }

    public void onDisable() {
        runnable.cancel();
        resetBlocks();
    }

    public void resetBlocks() {
        for (RegeneratingBlock block : blockList) {
            block.replace();
        }
    }

}
