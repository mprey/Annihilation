package me.mprey.listener;

import apple.util.JavaPluginSelector;
import me.mprey.Annihilation;
import org.bukkit.event.Listener;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class BaseListener implements Listener {

    public BaseListener() {
        Annihilation.getInstance().getServer().getPluginManager().registerEvents(this, Annihilation.getInstance());
    }

}
