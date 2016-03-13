package me.mprey.commands;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mason Prey on 2/19/16.
 */
public class SaveCommand extends BaseCommand {

    private List<BaseCommand> childCommands;

    public SaveCommand(Annihilation instance, BaseCommand... children) {
        super(instance);
        this.childCommands = Arrays.asList(children);
    }

    public String getCommand() {
        return "save";
    }

    public String getPermission() {
        return "save";
    }

    public String getName() {
        return Annihilation._l("commands.save.name");
    }

    public String getDescription() {
        return Annihilation._l("commands.save.description");
    }

    public String[] getArguments() {
        return null;
    }

    public boolean consoleFriendly() {
        return true;
    }

    public String getUsage() {
        return Annihilation._l("commands.save.usage");
    }

    public List<BaseCommand> getChildren() {
        return childCommands;
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if(!hasPermission(sender)) return false;

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
