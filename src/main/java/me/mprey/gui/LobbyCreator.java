package me.mprey.gui;

import me.mprey.Annihilation;
import me.mprey.ChatWriter;
import me.mprey.game.TeamColor;
import me.mprey.util.IconMenu;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.persistence.Lob;

/**
 * Created by Mason Prey on 3/6/16.
 */
public class LobbyCreator extends IconGUI {

    public LobbyCreator(Player player) {
        super(player);
    }

    //2 - load lobby from file
    //6 - create blank lobby world

    public IconMenu createMenu() {
        return new IconMenu(getName(), getSize(), new IconMenu.OptionClickEventHandler() {

            public void onOptionClick(IconMenu.OptionClickEvent event) {
                event.setWillClose(false);
                if (event.getPosition() == 2) {
                    event.setWillClose(true);
                    if (Annihilation.getInstance().getMapManager().lobbyMapExists()) {
                        if (Annihilation.getInstance().getMapManager().createLobbyFromFile()) {
                            getUser().sendMessage(ChatWriter.write(Annihilation._l("success.lobby.loaded")));
                        } else {
                            getUser().sendMessage(ChatWriter.write(Annihilation._l("errors.lobby.fail_to_load")));
                        }
                    } else {
                        getUser().sendMessage(ChatWriter.write(Annihilation._l("errors.lobby.file_not_found")));
                    }
                } else if (event.getPosition() == 6) {
                    event.setWillClose(true);
                    Annihilation.getInstance().getMapManager().createBlankLobby();
                    getUser().sendMessage(ChatWriter.write(Annihilation._l("success.lobby.created")));
                }
            }

        }, Annihilation.getInstance())
                .setOption(2, ItemUtil.nameAndLore(Material.BOOK, Annihilation._l("editor.icons.load_lobby.name"), Annihilation._ls("editor.icons.load_lobby.lore")))
                .setOption(6, ItemUtil.nameAndLore(Material.PAPER, Annihilation._l("editor.icons.blank_lobby.name"), Annihilation._ls("editor.icons.blank_lobby.lore")))
                .fillBlank(new ItemStack(Material.IRON_FENCE));
    }

    public ItemStack getTeamSign(TeamColor color) {
        return null; //TODO
    }

    public int getSize() {
        return 9;
    }

    public String getName() {
        return Annihilation._l("editor.lobby_creator.inventory_name");
    }
}
