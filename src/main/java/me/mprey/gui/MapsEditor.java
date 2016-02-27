package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.map.Map;
import me.mprey.util.GameUtil;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mason Prey on 2/25/16.
 */
public class MapsEditor extends IconGUI {

    private HashMap<Integer, Map> mapSlots;

    public MapsEditor(Player player) {
        super(player);
        this.mapSlots = new HashMap<>();
    }

    public IconMenu createMenu() {
        IconMenu menu =  new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {
            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 53) {
                    new MapCreator(getUser()).openGUI();
                } else if (event.getPosition() == 45) {
                    new MainEditor(getUser()).openGUI();
                } else {
                    Map clicked = getClickedMap(event.getPosition());
                    if (clicked != null) {

                    }
                }
            }
        }, Annihilation.getInstance())
                .setOption(53, getNewMapIcon())
                .setOption(45, getReturnIcon());
        return populate(menu);
    }

    public Map getClickedMap(int pos) {
        for (Integer i : mapSlots.keySet()) {
            if (pos == i) {
                return mapSlots.get(i);
            }
        }
        return null;
    }

    public IconMenu populate(IconMenu menu) {
        int slot = 0;
        for (Map map : Annihilation.getInstance().getMapManager().getMapList()) {
            if (slot == 45) {
                break;
            }

            ItemStack mapItem = new ItemStack(Material.MAP, slot);
            String name = (map.isValid() ? ChatColor.GREEN : ChatColor.RED) + "" + map.getName();
            List<String> lore = Annihilation._ls("editor.icons.map_icon.lore",
                    ImmutableMap.<String, String>builder()
                    .put("world", map.isWorldCopied() + "")
                    .put("green", map.getGreenTeamLocation().toString())
                    .put("red", map.getRedTeamLocation().toString())
                    .put("blue", map.getBlueTeamLocation().toString())
                    .put("yellow", map.getYellowTeamLocation().toString())
                    .put("diamonds", GameUtil.arrayToString(map.getDiamonds()))
                    .put("lobby", GameUtil.locationToString(map.getLobby()))
                    .build());

            mapSlots.put(slot, map);
            menu.setOption(slot, mapItem, name, lore.toArray(new String[0]));

            slot++;
        }
        return menu;
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.maps.inventory_name");
    }

    public ItemStack getNewMapIcon() {
        return ItemUtil.nameAndLore(new ItemStack(Material.PAPER, 1),
                Annihilation._l("editor.icons.new_map_icon.name"), Annihilation._ls("editor.icons.new_map_icon.lore"));
    }

}
