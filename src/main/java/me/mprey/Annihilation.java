package me.mprey;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Mason Prey on 2/6/16.
 */
public class Annihilation extends JavaPlugin {

    private static Annihilation instance;

    public void onEnable() {
        instance = this;
    }

    public void onDisable() {

    }

    public static Annihilation getInstance() {
        return instance;
    }

}
