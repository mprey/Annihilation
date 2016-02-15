package me.mprey.commands;

import me.mprey.Annihilation;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/14/16.
 */
public abstract class BaseCommand implements ICommand {

    private Annihilation instance;

    public BaseCommand(Annihilation instance) {
        this.instance = instance;
    }

    protected Annihilation getInstance() {
        return instance;
    }

    public abstract String getCommand();

    public abstract String getPermission();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String[] getArguments();

    public abstract boolean consoleFriendly();

    public abstract boolean execute(CommandSender sender, ArrayList<String> args);

    public boolean hasPermission(CommandSender sender) {
        if ((!(sender instanceof Player)) && !consoleFriendly()) {
            sender.sendMessage(ChatColor.RED + "no consoles!!"); //TODO format with locale
            return false;
        } else if (!sender.hasPermission("anni." + getPermission())) {
            sender.sendMessage(ChatColor.RED + "no permission!"); //TODO format with locale
            return false;
        }
        return true;
    }
}
