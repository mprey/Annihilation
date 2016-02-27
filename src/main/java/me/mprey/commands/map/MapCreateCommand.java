package me.mprey.commands.map;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.commands.MapCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
            File file = new File(Annihilation.getInstance().getServer().getWorldContainer().getAbsoluteFile(), map);
            if (file.exists()) {
                sender.sendMessage("exists");
            } else {
                sender.sendMessage("doesnt exist");
            }
        }
        return true;
    }
}
