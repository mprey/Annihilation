package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Mason Prey on 2/26/16.
 */
public class MapCreator extends IconGUI {

    private HashMap<Integer, String> map;

    public MapCreator(Player player) {
        super(player);
        this.map = new HashMap<>();
    }

    public IconMenu createMenu() {
        IconMenu menu =  new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 45) {
                    new MapsEditor(getUser()).openGUI();
                } else {
                    String worldName = getClickedWorld(event.getPosition());
                    if (worldName != null) {
                        if (Annihilation.getInstance().getMapManager().addMap(worldName)) {
                            new MapEditor(getUser(), Annihilation.getInstance().getMapManager().getMap(worldName)).openGUI();
                        } else {
                            getUser().sendMessage(ChatWriter.write(Annihilation._l("errors.map.already_exists", ImmutableMap.of("map", worldName))));
                            event.setWillClose(true);
                        }
                    }
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon());
        return populate(menu);
    }

    public IconMenu populate(IconMenu menu) {
        int slot = 0;
        for (String available : Annihilation.getInstance().getMapManager().getAvailableMaps()) {
            if (slot == 45) {
                break;
            }

            ItemStack icon = ItemUtil.nameAndLore(Material.PAPER, Annihilation._l("editor.icons.available_map_icon.name", ImmutableMap.of("name", available)), Annihilation._ls("editor.icons.available_map_icon.lore", ImmutableMap.of("name", available)));
            menu.setOption(slot, icon);

            map.put(slot, available);
            slot++;
        }
        return menu;
    }

    public String getClickedWorld(int pos) {
        for (Integer i : map.keySet()) {
            if (pos == i) {
                return map.get(i);
            }
        }
        return null;
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.map_creator.inventory_name");
    }
}
