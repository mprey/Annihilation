package me.mprey.commands.map;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.commands.BaseCommand;
import me.mprey.commands.MapCommand;
import me.mprey.map.Map;
import me.mprey.map.MapErrorCode;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/16/16.
 */
public class MapSaveCommand extends MapCommand {

    public MapSaveCommand(Annihilation instance) {
        super(instance);
    }

    public String getCommand() {
        return "save";
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
        String input = args.get(0);
        if (!getInstance().getMapManager().isMap(input)) {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.not_found", ImmutableMap.of("map", input))));
        } else {
            Map map = getInstance().getMapManager().getMap(input);
            MapErrorCode errorCode = getInstance().getMapManager().saveMap(map);
            if (errorCode == MapErrorCode.OK) {
                sender.sendMessage(ChatWriter.write(Annihilation._l("success.map.saved", ImmutableMap.of("map", input))));
            } else {
                sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.saving_error", ImmutableMap.of("map", input, "error", Annihilation._l("errors.map." + errorCode.getPath())))));
            }
        }
        return false;
    }

}
