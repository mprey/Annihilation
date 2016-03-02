package me.mprey.listener;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.game.Ender;
import me.mprey.game.TeamColor;
import me.mprey.map.Map;
import me.mprey.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Mason Prey on 3/1/16.
 */
public class MapCreatorListener extends BaseListener {

    /*
     * Ender Chest Listener
     */
    @EventHandler
    public void onPlaceChest(PlayerInteractEvent event) {

    }

    /*
     * Ender Furnace Listener
     */
    @EventHandler
    public void onPlaceFurnace(PlayerInteractEvent event) {

    }

    /*
     * Ender Brewer Listener
     */
    @EventHandler
    public void onPlaceBrewer(PlayerInteractEvent event) {

    }

    /*
     * Spawn Adder Listener
     */
    @EventHandler
    public void onPlaceSpawn(BlockPlaceEvent event) {
        if (event.getBlockPlaced() != null) {
            ItemStack im = event.getItemInHand();
            if (im != null && im.getType() == Material.WOOL && im.hasItemMeta() && im.getItemMeta().getDisplayName().equals(Annihilation._l("editor.icons.spawn_block.name"))) {
                event.setCancelled(true);
                String mapKey = ItemUtil.getData(im, "map");
                TeamColor team = TeamColor.valueOf(ItemUtil.getData(im, "team").toUpperCase());
                Map map = Annihilation.getInstance().getMapManager().getMap(mapKey);
                if (map != null) {
                    map.getTeamLocation(team).addSpawn(event.getBlockPlaced().getLocation());
                } else {
                    //TODO tell user no map
                }
            }
        }
    }

    /*
     * Region Wand Listener
     */
    @EventHandler
    public void onBlockInteractWand(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            ItemStack im = event.getItem();
            if (im != null && im.getType() == Material.WOOD_AXE && im.hasItemMeta() && im.getItemMeta().getDisplayName().equals(Annihilation._l("editor.icons.region_wand.name"))) {
                event.setCancelled(true);
                String mapKey = ItemUtil.getData(im, "map");
                TeamColor team = TeamColor.valueOf(ItemUtil.getData(im, "team").toUpperCase());
                Map map = Annihilation.getInstance().getMapManager().getMap(mapKey);
                if (map != null) {
                    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        map.getTeamLocation(team).getNexus().setMaxCorner(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage("set max corner");
                    } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        map.getTeamLocation(team).getNexus().setMinCorner(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage("set min corner");
                    }
                } else {
                    //TODO tell user no map
                }
            }
        }
    }

    /*
     * Diamonds Block Listener
     */
    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                ItemStack im = event.getItem();
                if (im != null && im.getType() == Material.DIAMOND_ORE && im.hasItemMeta() && im.getItemMeta().getDisplayName().equals(Annihilation._l("editor.icons.diamond_icon.name"))) {
                    if (event.getClickedBlock().getType() == Material.DIAMOND_ORE) {
                        String mapKey = ItemUtil.getData(im, "map");
                        Map map = Annihilation.getInstance().getMapManager().getMap(mapKey);
                        if (map != null) {
                            map.addDiamond(event.getClickedBlock().getLocation());
                            event.getPlayer().sendMessage("added a diamond ore for map " + map.getName());
                        } else {
                            //TODO tell player theres an unknown map
                        }
                    } else {
                        //TODO tell user they have to left-click a diamond-ore
                        event.getPlayer().sendMessage("not left-click diamond ore");
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

}
