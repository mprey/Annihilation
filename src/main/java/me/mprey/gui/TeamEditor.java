package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.game.Ender;
import me.mprey.game.TeamColor;
import me.mprey.map.Map;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/27/16.
 */
public class TeamEditor extends IconGUI {

    private Map map;

    private TeamColor color;

    //nexus region wand
    //spawn adder
    //ender furnace
    //ender brewer
    //ender chest

    public TeamEditor(Player player, Map map, TeamColor color)  {
        super(player);
        this.map = map;
        this.color = color;
    }

    public ItemStack getSpawnBlock(TeamColor color) {
        return ItemUtil.nameAndLore(color.getBlock(), Annihilation._l("editor.icons.spawn_block.name"), Annihilation._ls("editor.icons.spawn_block.lore"));
    }

    public ItemStack getEnder(Ender.EnderType type) {
        return ItemUtil.nameAndLore(type.getBlock(), Annihilation._l("editor.icons.ender_block.name", ImmutableMap.of("type", type.toString())), Annihilation._ls("editor.icons.ender_block.lore"));
    }

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 45) {
                    new MapEditor(getUser(), map).openGUI();
                } else if (event instanceof IconMenu.RemoveableOptionClickEvent) {
                    IconMenu.RemoveableOptionClickEvent e = (IconMenu.RemoveableOptionClickEvent) event;
                    if (e.getPosition() == 10) {
                        e.setCursor(ItemUtil.addData(getRegionWand(), ImmutableMap.of("map", map.getName(), "team", color.name())));
                    } else if (e.getPosition() == 16) {
                        e.setCursor(ItemUtil.addData(getSpawnBlock(color), ImmutableMap.of("map", map.getName(), "team", color.name())));
                    } else if (e.getPosition() == 28) {
                        e.setCursor(ItemUtil.addData(getEnder(Ender.EnderType.BREWER), ImmutableMap.of("map", map.getName(), "team", color.name())));
                    } else if (e.getPosition() == 31) {
                        e.setCursor(ItemUtil.addData(getEnder(Ender.EnderType.FURNACE), ImmutableMap.of("map", map.getName(), "team", color.name())));
                    } else if (e.getPosition() == 34) {
                        e.setCursor(ItemUtil.addData(getEnder(Ender.EnderType.CHEST), ImmutableMap.of("map", map.getName(), "team", color.name())));
                    }
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon())
                .setRemoveableOption(10, getRegionWand())
                .setRemoveableOption(16, getSpawnBlock(color))
                .setRemoveableOption(28, getEnder(Ender.EnderType.BREWER))
                .setRemoveableOption(31, getEnder(Ender.EnderType.FURNACE))
                .setRemoveableOption(34, getEnder(Ender.EnderType.CHEST))
                .fillBlank(new ItemStack(Material.STAINED_GLASS_PANE, 1, color.getColorData()));
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.team_editor.inventory_name", ImmutableMap.of("team", color.toString(), "map", map.getName()));
    }
}
