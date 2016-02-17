package me.mprey;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by Mason Prey on 2/15/16.
 */
public class ChatWriter {

    public static String write(String input) {
        return ChatColor.translateAlternateColorCodes('&', Annihilation.getInstance().getConfig().getString("chat_prefix", "[Annihilation]]")) + " " + ChatColor.WHITE + input;
    }

}
