package me.mprey.gui;

import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/25/16.
 */
public class MainEditor extends IconGUI {

    public MainEditor(final Player player) {
        super(player);
    }

    public IconMenu createMenu() {
        IconMenu menu = new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 0) {
                    new SettingsEditor(getUser()).openGUI();
                } else if (event.getPosition() == 4) {
                    getUser().sendMessage("opening lobby editor!");
                    event.setWillClose(true);
                } else if (event.getPosition() == 8) {
                    new MapsEditor(getUser()).openGUI();
                }
            }
        }, Annihilation.getInstance());
        return populate(menu);
    }

    public IconMenu populate(IconMenu menu) {
        ItemStack settingsIcon = ItemUtil.nameAndLore(new ItemStack(Material.NAME_TAG, 1), Annihilation._l("editor.icons.settings_icon.name"), Annihilation._ls("editor.icons.settings_icon.lore"));
        ItemStack mainLobbyIcon = ItemUtil.nameAndLore(new ItemStack(Material.BED), Annihilation._l("editor.icons.lobby_editor.name"), Annihilation._ls("editor.icons.lobby_editor.lore"));
        ItemStack mapIcon = ItemUtil.nameAndLore(new ItemStack(Material.MAP), Annihilation._l("editor.icons.maps_icon.name"), Annihilation._ls("editor.icons.maps_icon.lore"));

        menu.setOption(0, settingsIcon).setOption(4, mainLobbyIcon).setOption(8, mapIcon).fillBlank(new ItemStack(Material.IRON_FENCE));
        return menu;
    }

    public int getSize() {
        return 9;
    }

    public String getName() {
        return Annihilation._l("editor.main.inventory_name");
    }

}
