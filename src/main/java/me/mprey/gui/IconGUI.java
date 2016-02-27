package me.mprey.gui;

import me.mprey.Annihilation;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/25/16.
 */
public abstract class IconGUI {

    private Player player;

    private IconMenu menu;

    private ItemStack returnIcon;

    public IconGUI(Player player) {
        this.player = player;
        this.returnIcon = ItemUtil.nameAndLore(new ItemStack(Material.WOOD_DOOR),
                Annihilation._l("editor.icons.return_icon.name"), Annihilation._ls("editor.icons.return_icon.lore"));
    }

    public Player getUser() {
        return player;
    }

    public void openGUI() {
        getMenu().open(player);
    }

    public IconMenu getMenu() {
        if (menu == null) {
            this.menu = createMenu();
        }
        return this.menu;
    }

    public ItemStack getReturnIcon() {
        return this.returnIcon;
    }

    public abstract IconMenu createMenu();

    public abstract int getSize();

    public abstract String getName();

}
