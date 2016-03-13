package me.mprey.commands;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.map.Map;
import me.mprey.util.ItemUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 3/12/16.
 */
public class CheckCommand extends BaseCommand {

    public CheckCommand(Annihilation instance) {
        super(instance);
    }

    public String getCommand() {
        return "check";
    }

    public String getPermission() {
        return "check";
    }

    public String getName() {
        return Annihilation._l("commands.check.name");
    }

    public String getDescription() {
        return Annihilation._l("commands.check.description");
    }

    public String[] getArguments() {
        return new String[] {"map"};
    }

    public boolean consoleFriendly() {
        return false;
    }

    public boolean execute(CommandSender sender, ArrayList<String> args) {
        String mapKey = args.get(0);
        if (mapKey.equalsIgnoreCase("lobby")) {
            //TODO
        } else if(getInstance().getMapManager().isMap(mapKey)) {
            Map map = getInstance().getMapManager().getMap(mapKey);
            List<BaseComponent> messages = ItemUtil.getMapLore(map);
            for (BaseComponent component : messages) {
                ((Player) sender).spigot().sendMessage(component);
            }
        } else {
            sender.sendMessage(ChatWriter.write(Annihilation._l("errors.map.not_found", ImmutableMap.of("map", mapKey))));
        }
        return false;
    }

    public List<BaseCommand> getChildren() {
        return null;
    }

    public String getUsage() {
        return Annihilation._l("commands.check.usage");
    }
}
