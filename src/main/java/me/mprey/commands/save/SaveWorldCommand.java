package me.mprey.commands.save;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.commands.BaseCommand;
import me.mprey.map.Map;
import me.mprey.map.MapErrorCode;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 3/6/16.
 */
public class SaveWorldCommand extends BaseCommand {

    public SaveWorldCommand(Annihilation instance) {
        super(instance);
    }

    public String getCommand() {
        return "world";
    }

    public String getPermission() {
        return null;
    }

    public String getName() {
        return Annihilation._l("commands.save.world.name");
    }

    public String getDescription() {
        return Annihilation._l("commands.save.world.description");
    }

    public String[] getArguments() {
        return new String[] {"map"};
    }

    public boolean consoleFriendly() {
        return true;
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        if (args.get(0).equalsIgnoreCase("lobby")) {
            if (getInstance().getMapManager().lobbyLoaded()) {
                if (getInstance().getMapManager().getLobby().saveWorld()) {
                    sender.sendMessage(ChatWriter.write(Annihilation._l("success.map.saved", ImmutableMap.of("map", args.get(0)))));
                } else {
                    sender.sendMessage(ChatWriter.write(Annihilation._l("errors.lobby.unable_to_save")));
                }
            } else {
                sender.sendMessage(ChatWriter.write(Annihilation._l("errors.lobby.not_loaded")));
            }
        } else {
            Map map = getInstance().getMapManager().getMap(args.get(0));
            if (map != null) {
                MapErrorCode error = getInstance().getMapManager().saveMapWorld(map);
                if (error == MapErrorCode.OK) {
                    sender.sendMessage(ChatWriter.write(Annihilation._l("success.map.saved", ImmutableMap.of("map", args.get(0)))));
                } else {
                    sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.saving_error", ImmutableMap.of("error", error.toString(), "map", args.get(0)))));
                }
            } else {
                sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.not_found", ImmutableMap.of("map", args.get(0)))));
            }
        }
        return false;
    }

    public List<BaseCommand> getChildren() {
        return null;
    }

    public String getUsage() {
        return Annihilation._l("commands.save.world.usage");
    }

}
