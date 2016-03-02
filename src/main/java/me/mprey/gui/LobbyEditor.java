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

    //signs editor (red, blue, green, yellow)
    //inventory editor
    //load lobby world
    //teleport to lobby world
    //save map to file

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 45) {
                    new MainEditor(getUser()).openGUI();
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon());
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.lobby.inventory_name");
    }
}
