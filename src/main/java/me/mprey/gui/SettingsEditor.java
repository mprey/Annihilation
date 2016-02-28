package me.mprey.gui;

import me.mprey.Annihilation;
import me.mprey.util.IconMenu;
import org.bukkit.entity.Player;

/**
 * Created by Mason Prey on 2/26/16.
 */
public class SettingsEditor extends IconGUI {

    public SettingsEditor(Player player) {
        super(player);
    }

    //change locale
    //edit boolean for bungee mode
    //

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {
            @Override
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
        return Annihilation._l("editor.settings.inventory_name");
    }
}
