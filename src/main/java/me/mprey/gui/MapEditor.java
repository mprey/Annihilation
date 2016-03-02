package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.game.Ender;
import me.mprey.game.TeamColor;
import me.mprey.map.Map;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 2/26/16.
 */
public class MapEditor extends IconGUI {

    private final Map map;

    public MapEditor(Player player, Map map) {
        super(player);
        this.map = map;
    }

    //SLOT 2 - load world (grass block)
    //SLOT 6 - teleport to world
    //SLOT 19, 21, 23, 25 team editors
    //SLOT 38 - lobby editor
    //SLOT 39 - diamonds editor
    //SLOT 41 - save map
    //SLOT 42 - delete map

    //TODO add icon to edit the map

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 45) {
                    new MapsEditor(getUser()).openGUI();
                } else if (event.getPosition() == 2) {
                    if (!map.isWorldLoaded()) {
                        map.loadWorld();
                        getUser().sendMessage("world loaded");
                        //TODO tell player the world has been loaded
                    } else {
                        getUser().sendMessage("world already loaded.");
                        //TODO tell player the world was already loaded
                    }
                    event.setWillClose(true);
                } else if (event.getPosition() == 6) {
                    if (map.isWorldLoaded()) {
                        getUser().teleport(map.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        //TODO tell the player they teleported
                    } else {
                        getUser().sendMessage("world is not loaded.");
                        //TODO tell player world is not loaded
                    }
                    event.setWillClose(true);
                } else if (event.getPosition() == 38) {
                    new LobbyEditor(getUser()).openGUI();
                } else if (event.getPosition() == 41) {
                    new Confirmation(getUser(), Annihilation._l("confirm.save_map", ImmutableMap.of("map", map.getName())), "anni map save " + map.getName(), new MapEditor(getUser(), map)).openGUI();
                } else if (event.getPosition() == 42) {
                    new Confirmation(getUser(), Annihilation._l("confirm.delete_map", ImmutableMap.of("map", map.getName())), "anni map delete " + map.getName(), new MapEditor(getUser(), map)).openGUI();
                } else if (event.getPosition() == 19) {
                    new TeamEditor(getUser(), map, TeamColor.GREEN).openGUI();
                } else if (event.getPosition() == 21) {
                    new TeamEditor(getUser(), map, TeamColor.YELLOW).openGUI();
                } else if (event.getPosition() == 23) {
                    new TeamEditor(getUser(), map, TeamColor.RED).openGUI();
                } else if (event.getPosition() == 25) {
                    new TeamEditor(getUser(), map, TeamColor.BLUE).openGUI();
                } else if (event instanceof IconMenu.RemoveableOptionClickEvent) {
                    IconMenu.RemoveableOptionClickEvent re = (IconMenu.RemoveableOptionClickEvent) event;
                    ItemStack cursor = ItemUtil.addData(ItemUtil.nameAndLore(Material.DIAMOND_ORE, Annihilation._l("editor.icons.diamond_icon.name"), Annihilation._ls("editor.icons.diamond_icon.lore")), ImmutableMap.of("map", map.getName()));
                    re.setCursor(cursor);
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon())
                .setOption(19, getTeamEditorBlock(TeamColor.GREEN))
                .setOption(21, getTeamEditorBlock(TeamColor.YELLOW))
                .setOption(23, getTeamEditorBlock(TeamColor.RED))
                .setOption(25, getTeamEditorBlock(TeamColor.BLUE))
                .setOption(2, ItemUtil.nameAndLore(Material.GRASS, Annihilation._l("editor.icons.world_loader.name"), Annihilation._ls("editor.icons.world_loader.lore")))
                .setOption(38, ItemUtil.nameAndLore(Material.STRING, Annihilation._l("editor.icons.lobby_editor.name"), Annihilation._ls("editor.icons.lobby_editor.lore")))
                .setRemoveableOption(39, ItemUtil.nameAndLore(Material.DIAMOND_ORE, Annihilation._l("editor.icons.diamond_icon.name"), Annihilation._ls("editor.icons.diamond_icon.lore")))
                .setOption(6, ItemUtil.nameAndLore(Material.ENDER_PEARL, Annihilation._l("editor.icons.teleport_icon.name"), Annihilation._ls("editor.icons.teleport_icon.lore")))
                .setOption(41, ItemUtil.nameAndLore(Material.EMERALD_BLOCK, Annihilation._l("editor.icons.save_icon.name"), Annihilation._ls("editor.icons.save_icon.lore")))
                .setOption(42, ItemUtil.nameAndLore(Material.REDSTONE_BLOCK, Annihilation._l("editor.icons.delete_icon.name"), Annihilation._ls("editor.icons.delete_icon.lore")));
    }

    public ItemStack getTeamEditorBlock(TeamColor color) {
        return ItemUtil.nameAndLore(color.getBlock(), Annihilation._l("editor.icons.team_editor.name", ImmutableMap.of("team", color.toString())), Annihilation._ls("editor.icons.team_block.lore"));
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.map_editor.inventory_name", ImmutableMap.of("map", map.getName()));
    }
}
