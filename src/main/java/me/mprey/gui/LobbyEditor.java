package me.mprey.gui;

import me.mprey.Annihilation;
import me.mprey.util.IconMenu;
import org.bukkit.entity.Player;

/**
 * Created by Mason Prey on 2/27/16.
 */
public class LobbyEditor extends IconGUI {

    public LobbyEditor(Player player) {
        super(player);
    }

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {

            }
        }, Annihilation.getInstance());
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.lobby.inventory_name");
    }
}
