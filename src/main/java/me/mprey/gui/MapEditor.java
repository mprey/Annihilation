package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
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

    //SLOT 39 - load world (grass block)
    //SLOT 12 - teleport to world
    //SLOT 10,19,28,37 team editors
    //SLOT 14 - lobby editor
    //SLOT 41 - diamonds editor
    //SLOT 16 - save data to file
    //SLOT 43 - save map to file
    //SLOT 53 - delete map

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 45) {
                    new MapsEditor(getUser()).openGUI();
                } else if (event.getPosition() == 39) {
                    if (!map.isWorldLoaded()) {
                        map.loadWorld();
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("success.map.loaded_world", ImmutableMap.of("map", map.getName()))));
                    } else {
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("errors.map.map_world_loaded", ImmutableMap.of("map", map.getName()))));
                    }
                    event.setWillClose(true);
                } else if (event.getPosition() == 12) {
                    if (map.isWorldLoaded()) {
                        getUser().teleport(map.getWorld().getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("success.map.teleported_world", ImmutableMap.of("map", map.getName()))));
                    } else {
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("errors.map.map_world_not_loaded", ImmutableMap.of("map", map.getName()))));
                    }
                    event.setWillClose(true);
                } else if (event.getPosition() == 14) {
                    new LobbyEditor(getUser()).openGUI();
                } else if (event.getPosition() == 16) {
                    new Confirmation(getUser(), Annihilation._l("confirm.save_data", ImmutableMap.of("map", map.getName())), "anni save data " + map.getName(), new MapEditor(getUser(), map)).openGUI();
                } else if (event.getPosition() == 53) {
                    new Confirmation(getUser(), Annihilation._l("confirm.delete_map", ImmutableMap.of("map", map.getName())), "anni delete " + map.getName(), new MapEditor(getUser(), map)).openGUI();
                } else if (event.getPosition() == 10) {
                    new TeamEditor(getUser(), map, TeamColor.GREEN).openGUI();
                } else if (event.getPosition() == 19) {
                    new TeamEditor(getUser(), map, TeamColor.YELLOW).openGUI();
                } else if (event.getPosition() == 28) {
                    new TeamEditor(getUser(), map, TeamColor.RED).openGUI();
                } else if (event.getPosition() == 37) {
                    new TeamEditor(getUser(), map, TeamColor.BLUE).openGUI();
                } else if (event.getPosition() == 43) {
                    new Confirmation(getUser(), Annihilation._l("confirm.save_world", ImmutableMap.of("map", map.getName())), "anni save world " + map.getName(), new MapEditor(getUser(), map)).openGUI();
                } else if (event.getPosition() == 16) {
                    new Confirmation(getUser(), Annihilation._l("confirm.save_data", ImmutableMap.of("map", map.getName())), "anni save data " + map.getName(), new MapEditor(getUser(), map)).openGUI();
                } else if (event instanceof IconMenu.RemoveableOptionClickEvent) {
                    IconMenu.RemoveableOptionClickEvent re = (IconMenu.RemoveableOptionClickEvent) event;
                    ItemStack cursor = ItemUtil.addData(ItemUtil.nameAndLore(Material.DIAMOND_ORE, Annihilation._l("editor.icons.diamond_icon.name"), Annihilation._ls("editor.icons.diamond_icon.lore")), ImmutableMap.of("map", map.getName()));
                    re.setCursor(cursor);
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon())
                .setOption(10, getTeamEditorBlock(TeamColor.GREEN))
                .setOption(19, getTeamEditorBlock(TeamColor.YELLOW))
                .setOption(28, getTeamEditorBlock(TeamColor.RED))
                .setOption(37, getTeamEditorBlock(TeamColor.BLUE))
                .setOption(39, ItemUtil.nameAndLore(Material.GRASS, Annihilation._l("editor.icons.world_loader.name"), Annihilation._ls("editor.icons.world_loader.lore")))
                .setOption(14, ItemUtil.nameAndLore(Material.STRING, Annihilation._l("editor.icons.lobby_editor.name"), Annihilation._ls("editor.icons.lobby_editor.lore")))
                .setRemoveableOption(41, ItemUtil.nameAndLore(Material.DIAMOND_ORE, Annihilation._l("editor.icons.diamond_icon.name"), Annihilation._ls("editor.icons.diamond_icon.lore")))
                .setOption(12, ItemUtil.nameAndLore(Material.ENDER_PEARL, Annihilation._l("editor.icons.teleport_icon.name"), Annihilation._ls("editor.icons.teleport_icon.lore")))
                .setOption(16, ItemUtil.nameAndLore(Material.EMERALD_BLOCK, Annihilation._l("editor.icons.save_data_icon.name"), Annihilation._ls("editor.icons.save_data_icon.lore")))
                .setOption(53, ItemUtil.nameAndLore(Material.REDSTONE_BLOCK, Annihilation._l("editor.icons.delete_icon.name"), Annihilation._ls("editor.icons.delete_icon.lore")))
                .setOption(43, ItemUtil.nameAndLore(Material.EMERALD_BLOCK, Annihilation._l("editor.icons.save_world_icon.name"), Annihilation._ls("editor.icons.save_world_icon.lore")))
                .fillBlank(new ItemStack(Material.STAINED_GLASS_PANE));
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
