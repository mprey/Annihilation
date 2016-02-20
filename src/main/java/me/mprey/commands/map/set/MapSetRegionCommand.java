package me.mprey.commands.map.set;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.commands.map.MapSetCommand;
import me.mprey.map.Map;
import me.mprey.util.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/19/16.
 */
public class MapSetRegionCommand extends MapSetCommand {

    public MapSetRegionCommand(Annihilation instance) {
        super(instance);
    }

    public String getCommand() {
        return "region";
    }

    public String[] getArguments() {
        return new String[]{"point", "map"};
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
        String point = args.get(0);
        String input = args.get(1);
        if (!getInstance().getMapManager().isMap(input)) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.not_found", ImmutableMap.of("map", input))));
        } else if (!point.equalsIgnoreCase("min") && !point.equalsIgnoreCase("max")) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.syntax.min_max")));
        } else {
            Map map = getInstance().getMapManager().getMap(input);
            if (point.equalsIgnoreCase("min")) {
                map.getMapArea().setMinCorner(((Player) sender).getLocation());
                sender.sendMessage(ChatWriter.write(Annihilation._l("success.map.set_min_point")));
            } else {
                map.getMapArea().setMaxCorner(((Player) sender).getLocation());
                sender.sendMessage(ChatWriter.write(Annihilation._l("success.map.set_max_point")));
            }
        }
        return false;
    }

}