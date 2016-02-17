package me.mprey.commands;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
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
            sender.sendMessage("/anni help");
            return true;
        }
        String command = args[0];

        ArrayList<String> argList = new ArrayList<>(Arrays.asList(args));
        argList.remove(0); //remove the command label

        for (BaseCommand baseCommand : commands) {
            if (baseCommand.getCommand().equalsIgnoreCase(command)) {
                if (baseCommand.getArguments() != null && baseCommand.getArguments().length > argList.size()) {
                    sender.sendMessage(ChatWriter.write(Annihilation._l("errors.commands.invalid_args", ImmutableMap.of("usage", baseCommand.getUsage()))));
                    return false;
                }
                return baseCommand.execute(sender, argList);
            }
        }
        sender.sendMessage("/anni help");
        return false;
    }
}
