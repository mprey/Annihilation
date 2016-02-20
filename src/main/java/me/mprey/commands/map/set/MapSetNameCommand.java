package me.mprey.commands.map.set;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.commands.map.MapSetCommand;
import me.mprey.map.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/19/16.
 */
public class MapSetNameCommand extends MapSetCommand {

    public MapSetNameCommand(Annihilation instance) {
        super(instance);
    }

    public String getCommand() {
        return "name";
    }

    public String[] getArguments() {
        return new String[]{"name", "map"};
    }

    public boolean consoleFriendly() {
        return false;
    }

    public String getName() {
        return Annihilation._l("commands.map.set." + getCommand() + ".name");
    }

    public String getDescription() {
        return Annihilation._l("commands.map.set." + getCommand() + ".description");
    }

    public String getUsage() {
        return Annihilation._l("commands.map.set." + getCommand() + ".usage");
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        String newName = args.get(0);
        String map = args.get(1);
        if (!getInstance().getMapManager().isMap(map)) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.not_found", ImmutableMap.of("map", map))));
        } else if (getInstance().getMapManager().isMap(newName)) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.already_exists", ImmutableMap.of("map", newName))));
        } else {
            Map mapObject = getInstance().getMapManager().getMap(map);
            mapObject.setName(newName);
            sender.sendMessage(ChatWriter.write(Annihilation._l("success.map.set_name", ImmutableMap.of("map", map, "new_name", newName))));
        }
        return false;
    }

}