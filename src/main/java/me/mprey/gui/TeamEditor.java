package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.game.Ender;
import me.mprey.game.TeamColor;
import me.mprey.map.Map;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/27/16.
 */
public class TeamEditor extends IconGUI {

    private Map map;

    private TeamColor color;

    public TeamEditor(Player player, Map map, TeamColor color)  {
        super(player);
        this.map = map;
        this.color = color;
    }

    public ItemStack getSpawnBlock(TeamColor color) {
        return ItemUtil.nameAndLore(color.getBlock(), Annihilation._l("editor.icons.spawn_block.name", ImmutableMap.of("team", color.toString())), Annihilation._ls("editor.spawn_block.lore"));
    }

    public ItemStack getEnder(Ender.EnderType type) {
        return ItemUtil.nameAndLore(type.getBlock(), Annihilation._l("editor.icons.ender_block.name", ImmutableMap.of("type", type.toString())), Annihilation._ls("editor.ender_block.lore"));
    }

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 45) {
                    new MapEditor(getUser(), map).openGUI();
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon());
        //TODO populate icon menu
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.team_editor.inventory_name", ImmutableMap.of("team", color.toString(), "map", map.getName()));
    }
}
