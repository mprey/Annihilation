package me.mprey;

import me.mprey.regen.RegeneratingBlock;
import me.mprey.regen.RegeneratingBlockEffect;
import me.mprey.regen.RegeneratingBlockManager;
import me.mprey.regen.RegeneratingBlockStructure;
import me.mprey.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Mason Prey on 2/6/16.
 */
public class Annihilation extends JavaPlugin {

    private static Annihilation instance;

    private RegeneratingBlockManager regeneratingBlockManager;

    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        this.createRegeneratingBlocksManager();


    }

    public void onDisable() {
        regeneratingBlockManager.onDisable();
    }

    public void createRegeneratingBlocksManager() {
        long interval = this.getConfig().getLong("regen_blocks.interval");
        this.regeneratingBlockManager = new RegeneratingBlockManager(ConfigUtil.getRegeneratingBlockStructures(this.getConfig(), "regen_blocks.blocks"), interval);
    }

    public static Annihilation getInstance() {
        return instance;
    }

}
