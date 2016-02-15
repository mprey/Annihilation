package me.mprey.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

/**
 * Created by Mason Prey on 2/14/16.
 */
public interface ICommand {

    String getCommand();

    String getPermission();

    String getName();

    String getUsage();

    String getDescription();

    String[] getArguments();

    boolean hasPermission(CommandSender sender);

    boolean consoleFriendly();

    boolean execute(CommandSender sender, ArrayList<String> args);

}