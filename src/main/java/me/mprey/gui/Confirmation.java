package me.mprey.gui;

import me.mprey.Annihilation;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/26/16.
 */
public class Confirmation extends IconGUI {

    private String prompt;

    private IconGUI confirmed, denied;

    private static int[] green = {
            27, 28, 29, 30,
            36, 37, 38, 39,
            45, 46, 47, 48
    };

    private static int[] red = {
            32, 33, 34, 35,
            41, 42, 43, 44,
            50, 51, 52, 53
    };

    public Confirmation(Player player, String prompt, IconGUI confirmed, IconGUI denied) {
        super(player);
        this.prompt = prompt;
        this.confirmed = confirmed;
        this.denied = denied;
    }

    public IconMenu createMenu() {
        IconMenu menu =  new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (isConfirmed(event.getPosition())) {
                    confirmed.openGUI();
                } else if (isDenied(event.getPosition())) {
                    denied.openGUI();
                }
            }

        }, Annihilation.getInstance())
                .setOption(13, getPrompter());
        return populate(menu);
    }

    public boolean isConfirmed(int pos) {
        for (int i : green) {
            if (i == pos) {
                return true;
            }
        }
        return false;
    }

    public boolean isDenied(int pos) {
        for (int i : red) {
            if (i == pos) {
                return true;
            }
        }
        return false;
    }

    public IconMenu populate(IconMenu menu) {
        for (int i : green) {
            menu.setOption(i, new ItemStack(Material.EMERALD_BLOCK), Annihilation._l("editor.icons.confirm_green.name"), new String[0]);
        }
        for (int i : red) {
            menu.setOption(i, new ItemStack(Material.REDSTONE_BLOCK), Annihilation._l("editor.icons.confirm_red.name"), new String[0]);
        }
        return menu;
    }

    public ItemStack getPrompter() {
        return ItemUtil.nameAndLore(new ItemStack(Material.SIGN), prompt, Annihilation._ls("editor.icons.confirm_sign.lore"));
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.confirmation.inventory_name");
    }
}
