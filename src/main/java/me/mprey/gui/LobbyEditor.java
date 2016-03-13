package me.mprey.gui;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.game.TeamColor;
import me.mprey.map.Lobby;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Created by Mason Prey on 2/27/16.
 */
public class LobbyEditor extends IconGUI {

    public LobbyEditor(Player player) {
        super(player);
    }

    //signs editor (red, blue, green, yellow) 4x - 10,19,28,37
    //inventory editor 1x - 12
    //spawn setter 1x - 39
    //load lobby world 1x - 14
    //teleport to lobby world 1x - 41
    //save world to file 1x - 43
    //save data to file 1x - 16
    //wool indicator (red, blue, green, yellow) 4x - 9, 18, 27, 36

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);

                Lobby map = Annihilation.getInstance().getMapManager().getLobby();
                if (map == null) {
                    return;
                }

                if (event.getPosition() == 45) {
                    new MainEditor(getUser()).openGUI();
                } else if (event instanceof IconMenu.RemoveableOptionClickEvent) {
                    IconMenu.RemoveableOptionClickEvent re = (IconMenu.RemoveableOptionClickEvent) event;
                    if (re.getPosition() == 10) {
                        re.setCursor(ItemUtil.addData(ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")), ImmutableMap.of("team", TeamColor.RED.name())));
                    } else if (re.getPosition() == 19) {
                        re.setCursor(ItemUtil.addData(ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")), ImmutableMap.of("team", TeamColor.BLUE.name())));
                    } else if (re.getPosition() == 28) {
                        re.setCursor(ItemUtil.addData(ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")), ImmutableMap.of("team", TeamColor.GREEN.name())));
                    } else if (re.getPosition() == 37) {
                        re.setCursor(ItemUtil.addData(ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")), ImmutableMap.of("team", TeamColor.YELLOW.name())));
                    } else if (re.getPosition() == 39) {
                        re.setCursor(ItemUtil.nameAndLore(Material.WOOL, Annihilation._l("editor.icons.lobby_spawn.name"), Annihilation._ls("editor.icons.lobby_spawn.lore")));
                    }
                } else if (event.getPosition() == 12) { //open inventory editor
                    //TODO open inventory editor
                } else if (event.getPosition() == 14) { //load world
                    if (!map.isWorldLoaded()) {
                        map.loadWorld();
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("success.map.loaded_world", ImmutableMap.of("map", Lobby.FILE_NAME))));
                    } else {
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("errors.map.map_world_loaded", ImmutableMap.of("map", Lobby.FILE_NAME))));
                    }
                    event.setWillClose(true);
                } else if (event.getPosition() == 41) { //teleport world
                    if (map.isWorldLoaded()) {
                        getUser().teleport(map.getSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("success.map.teleported_world", ImmutableMap.of("map", Lobby.FILE_NAME))));
                    } else {
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("errors.map.map_world_not_loaded", ImmutableMap.of("map", Lobby.FILE_NAME))));
                    }
                    event.setWillClose(true);
                } else if (event.getPosition() == 43) { //save world
                    new Confirmation(getUser(), Annihilation._l("confirm.save_world", ImmutableMap.of("map", Lobby.FILE_NAME)), "anni save world " + Lobby.FILE_NAME, new LobbyEditor(getUser())).openGUI();
                } else if (event.getPosition() == 16) { //save data
                    new Confirmation(getUser(), Annihilation._l("confirm.save_data", ImmutableMap.of("map", Lobby.FILE_NAME)), "anni save data " + Lobby.FILE_NAME, new LobbyEditor(getUser())).openGUI();
                }
            }

        }, Annihilation.getInstance())
                .setOption(45, getReturnIcon())
                .setOption(9, ItemUtil.nameAndLore(TeamColor.RED.getBlock(), TeamColor.RED.toString(), Arrays.asList("")))
                .setOption(18, ItemUtil.nameAndLore(TeamColor.BLUE.getBlock(), TeamColor.BLUE.toString(), Arrays.asList("")))
                .setOption(27, ItemUtil.nameAndLore(TeamColor.GREEN.getBlock(), TeamColor.GREEN.toString(), Arrays.asList("")))
                .setOption(36, ItemUtil.nameAndLore(TeamColor.YELLOW.getBlock(), TeamColor.YELLOW.toString(), Arrays.asList("")))
                .setRemoveableOption(10, ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")))
                .setRemoveableOption(19, ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")))
                .setRemoveableOption(28, ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")))
                .setRemoveableOption(37, ItemUtil.nameAndLore(Material.SIGN, Annihilation._l("editor.icons.team_sign.name"), Annihilation._ls("editor.icons.team_sign.lore")))
                .setOption(12, ItemUtil.nameAndLore(Material.CHEST, Annihilation._l("editor.icons.inventory_editor.name"), Annihilation._ls("editor.icons.inventory_editor.lore")))
                .setRemoveableOption(39, ItemUtil.nameAndLore(Material.WOOL, Annihilation._l("editor.icons.lobby_spawn.name"), Annihilation._ls("editor.icons.lobby_spawn.lore")))
                .setOption(14, ItemUtil.nameAndLore(Material.GRASS, Annihilation._l("editor.icons.world_loader.name"), Annihilation._ls("editor.icons.world_loader.lore")))
                .setOption(41, ItemUtil.nameAndLore(Material.ENDER_PEARL, Annihilation._l("editor.icons.teleport_icon.name"), Annihilation._ls("editor.icons.teleport_icon.lore")))
                .setOption(43, ItemUtil.nameAndLore(Material.EMERALD_BLOCK, Annihilation._l("editor.icons.save_world_icon.name"), Annihilation._ls("editor.icons.save_world_icon.lore")))
                .setOption(16, ItemUtil.nameAndLore(Material.EMERALD_BLOCK, Annihilation._l("editor.icons.save_data_icon.name"), Annihilation._ls("editor.icons.save_data_icon.lore")))
                .fillBlank(new ItemStack(Material.IRON_FENCE));
    }

    public int getSize() {
        return 54;
    }

    public String getName() {
        return Annihilation._l("editor.lobby.inventory_name");
    }
}
