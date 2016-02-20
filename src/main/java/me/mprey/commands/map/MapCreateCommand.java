package me.mprey.commands.map;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.commands.MapCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/14/16.
 */
public class MapCreateCommand extends MapCommand {

    public MapCreateCommand(Annihilation instance) {
        super(instance);
    }

    public String getCommand() {
        return "create";
    }

    public String[] getArguments() {
        return new String[]{"map"};
    }

    public boolean consoleFriendly() {
        return true;
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
        String map = args.get(0);
        if (getInstance().getMapManager().isMap(map)) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.already_exists", ImmutableMap.of("map", map))));
            return false;
        } else {
            getInstance().getMapManager().addMap(map);
            sender.sendMessage(ChatWriter.write(Annihilation._l("success.map.added", ImmutableMap.of("map", map))));
        }
        return true;
    }
}
