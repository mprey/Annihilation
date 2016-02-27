package me.mprey.commands;

import me.mprey.Annihilation;
import me.mprey.gui.MainEditor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 2/25/16.
 */
public class GUICommand extends BaseCommand {

    public GUICommand(Annihilation instance) {
        super(instance);
    }

    public String getCommand() {
        return "gui";
    }

    public String getPermission() {
        return "gui.use";
    }

    public String getName() {
        return Annihilation._l("commands.gui.name");
    }

    public String getDescription() {
        return Annihilation._l("commands.gui.description");
    }

    public String[] getArguments() {
        return new String[0];
    }

    public boolean consoleFriendly() {
        return false;
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        new MainEditor((Player) sender).openGUI();
        return false;
    }

    public List<BaseCommand> getChildren() {
        return null;
    }

    public String getUsage() {
        return Annihilation._l("commands.gui.usage");
    }
}


