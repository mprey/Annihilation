package me.mprey.commands;

import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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

    public abstract List<BaseCommand> getChildren();

    public boolean hasPermission(CommandSender sender) {
        if ((!(sender instanceof Player)) && !consoleFriendly()) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.commands.no_console")));
            return false;
        } else if (!sender.hasPermission("anni." + getPermission())) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.commands.no_permission")));
            return false;
        }
        return true;
    }
}
