package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.map.Map;
import me.mprey.util.IconMenu;
import org.bukkit.entity.Player;

/**
 * Created by Mason Prey on 2/26/16.
 */
public class MapEditor extends IconGUI {

    private Map map;

    public MapEditor(Player player, Map map) {
        super(player);
        this.map = map;
    }

    //SLOT 38 - save map
    //SLOT 42 - delete map

    //SLOT 19,20,21,22,23,24,25 red,blue,green,yellow,furnace,chest,brewer
    //SLOT 13 teleport to world

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 45) {
                    new MapsEditor(getUser()).openGUI();
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon());
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.map_editor.inventory_name", ImmutableMap.of("map", map.getName()));
    }
}
