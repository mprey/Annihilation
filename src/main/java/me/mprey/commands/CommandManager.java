package me.mprey.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mason Prey on 2/14/16.
 */
public class CommandManager implements CommandExecutor {

    private List<BaseCommand> commands;

    public CommandManager(BaseCommand... commands) {
        this.commands = Arrays.asList(commands);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }
        String command = args[0];

        ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
        argList.remove(0); //remove the command label

        for (BaseCommand baseCommand : commands) {
            if (baseCommand.getCommand().equalsIgnoreCase(command)) {
                if (baseCommand.getArguments().length > argList.size()) {
                    sender.sendMessage("WRONG ARGS!"); //TODO locale
                    return false;
                }
                return baseCommand.execute(sender, argList);
            }
        }

        return false;
    }
}
