package me.mprey.commands.map;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.commands.BaseCommand;
import me.mprey.commands.MapCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 2/14/16.
 */
public class MapSetCommand extends MapCommand {

    public MapSetCommand(Annihilation instance, BaseCommand... children) {
        super(instance, children);
    }

    public String getCommand() {
        return "set";
    }

    public String[] getArguments() {
        return null;
    }

    public boolean consoleFriendly() {
        return false;
    }

    public String getName() {
        return Annihilation._l("commands.map." + getCommand() + ".name");
    }

    public String getDescription() {
        return Annihilation._l("commands.map." + getCommand() + ".description");
    }

    public String getUsage() {
        return Annihilation._l("commands.map." + getCommand() + ".usage");
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (args.size() == 0) {
            sender.sendMessage(ChatWriter.write(getUsage()));
            return false;
        } else {
            String subCommand = args.remove(0);
            for (BaseCommand child : getChildren()) {
                if (subCommand.equalsIgnoreCase(child.getCommand())) {
                    if (child.getArguments() != null && child.getArguments().length == args.size()) {
                        child.execute(sender, args);
                    } else {
                        sender.sendMessage(ChatWriter.write(Annihilation._l("errors.commands.invalid_args", ImmutableMap.of("usage", child.getUsage()))));
                    }
                    return false;
                }
            }
        }
        sender.sendMessage(ChatWriter.write(Annihilation._l("errors.commands.unknown_command")));
        return false;
    }
}
