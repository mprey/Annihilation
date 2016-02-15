package me.mprey.commands;

import me.mprey.Annihilation;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/14/16.
 */
public class AddMapCommand extends BaseCommand {

    public AddMapCommand(Annihilation instance) {
        super(instance);
    }

    @Override
    public String getCommand() {
        return "addmap";
    }

    @Override
    public String getPermission() {
        return "setup";
    }

    @Override
    public String getName() {
        return "addmap"; //TODO locale
    }

    @Override
    public String getDescription() {
        return "adds a map"; //TODO locale
    }

    @Override
    public String[] getArguments() {
        return new String[]{"map"};
    }

    @Override
    public boolean consoleFriendly() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, ArrayList<String> args) {
        String map = args.get(0);
        if (getInstance().getMapManager().isMap(map)) {
            sender.sendMessage("MAP EXISTS!"); //TODO locale
            return false;
        } else {
            getInstance().getMapManager().addMap(map);
            sender.sendMessage("added map " + map + "! YA"); //TODO locale
        }
        return true;
    }
}
