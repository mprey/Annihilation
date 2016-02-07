package me.mprey;

import me.mprey.regen.RegeneratingBlock;
import me.mprey.regen.RegeneratingBlockEffect;
import me.mprey.regen.RegeneratingBlockManager;
import me.mprey.regen.RegeneratingBlockStructure;
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

        this.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onBreak(BlockBreakEvent event) {
                regeneratingBlockManager.handleBlockBreak(event);
            }

            @EventHandler
            public void onInteract(PlayerInteractEvent event) {
                if (event.getClickedBlock() != null) {
                    regeneratingBlockManager.handleInteract(event);
                }
            }

        }, this);
    }

    public void onDisable() {
        regeneratingBlockManager.onDisable();
    }

    public void createRegeneratingBlocksManager() {
        long interval = this.getConfig().getLong("regen_blocks.interval");

        ArrayList<RegeneratingBlockStructure> structureList = new ArrayList<>();

        if (this.getConfig().isConfigurationSection("regen_blocks.blocks")) {
            for (String key : this.getConfig().getConfigurationSection("regen_blocks.blocks").getKeys(false)) {
                try {
                    Material material = Material.getMaterial(this.getConfig().getString("regen_blocks.blocks." + key + ".type"));
                    Material reward = Material.getMaterial(this.getConfig().getString("regen_blocks.blocks." + key + ".reward"));
                    int rewardInt = this.getConfig().getInt("regen_blocks.blocks." + key + ".reward_amount");
                    long delay = this.getConfig().getInt("regen_blocks.blocks." + key + ".delay") * 20L;
                    boolean naturalDrop = this.getConfig().getBoolean("regen_blocks.blocks." + key + ".natural_drop");
                    Material placeholder = Material.getMaterial(this.getConfig().getString("regen_blocks.blocks." + key + ".place_holder"));
                    int exp = this.getConfig().getInt("regen_blocks.blocks." + key + ".exp_reward");
                    Sound sound = Sound.valueOf(this.getConfig().getString("regen_blocks.blocks." + key + ".sound").toUpperCase());
                    RegeneratingBlockEffect effect = RegeneratingBlockEffect.valueOf(this.getConfig().getString("regen_blocks.blocks." + key + ".effect").toUpperCase());
                    structureList.add(new RegeneratingBlockStructure(material, reward, rewardInt, delay, naturalDrop, placeholder, exp, sound, effect));
                } catch (Exception e) {
                    this.getLogger().log(Level.SEVERE, "Error while reading regeneration block section: " + key);
                    e.printStackTrace();
                }
            }
        }

        this.regeneratingBlockManager = new RegeneratingBlockManager(structureList, interval);
    }

    public static Annihilation getInstance() {
        return instance;
    }

}
