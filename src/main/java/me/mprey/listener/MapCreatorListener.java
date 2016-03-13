package me.mprey.listener;

import com.google.common.collect.ImmutableMap;
import me.mprey.Annihilation;
import me.mprey.ChatWriter;
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
        if (event.getClickedBlock() != null) {
            ItemStack im = event.getItem();
            if (im != null && im.getType() == Material.CHEST && im.hasItemMeta() && im.getItemMeta().getDisplayName().equals(Annihilation._l("editor.icons.ender_block", ImmutableMap.of("type", Ender.EnderType.CHEST.toString())))) {
                if (event.getClickedBlock().getType() == Material.CHEST) {
                    event.setCancelled(true);
                    String mapKey = ItemUtil.getData(im, "map");
                    TeamColor team = TeamColor.valueOf(ItemUtil.getData(im, "team").toUpperCase());
                    Map map = Annihilation.getInstance().getMapManager().getMap(mapKey);
                    if (map != null) {
                        map.getTeamLocation(team).setEnderChest(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("success.map.set_ender_chest", ImmutableMap.of("map", mapKey, "team", team.toString()))));
                    } else {
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("errors.not_found", ImmutableMap.of("map", mapKey))));
                    }
                }
            }
        }
    }

    /*
     * Ender Furnace Listener
     */
    @EventHandler
    public void onPlaceFurnace(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            ItemStack im = event.getItem();
            if (im != null && im.getType() == Material.FURNACE && im.hasItemMeta() && im.getItemMeta().getDisplayName().equals(Annihilation._l("editor.icons.ender_block.name", ImmutableMap.of("type", Ender.EnderType.FURNACE.toString())))) {
                if (event.getClickedBlock().getType() == Material.FURNACE) {
                    event.setCancelled(true);
                    String mapKey = ItemUtil.getData(im, "map");
                    TeamColor team = TeamColor.valueOf(ItemUtil.getData(im, "team").toUpperCase());
                    Map map = Annihilation.getInstance().getMapManager().getMap(mapKey);
                    if (map != null) {
                        map.getTeamLocation(team).setEnderFurnace(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("success.map.set_ender_furnace", ImmutableMap.of("map", mapKey, "team", team.toString()))));
                    } else {
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("errors.not_found", ImmutableMap.of("map", mapKey))));
                    }
                }
            }
        }
    }

    /*
     * Ender Brewer Listener
     */
    @EventHandler
    public void onPlaceBrewer(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            ItemStack im = event.getItem();
            if (im != null && im.getType() == Material.BREWING_STAND_ITEM && im.hasItemMeta() && im.getItemMeta().getDisplayName().equals(Annihilation._l("editor.icons.ender_block.name", ImmutableMap.of("type", Ender.EnderType.BREWER.toString())))) {
                if (event.getClickedBlock().getType() == Material.BREWING_STAND_ITEM) {
                    event.setCancelled(true);
                    String mapKey = ItemUtil.getData(im, "map");
                    TeamColor team = TeamColor.valueOf(ItemUtil.getData(im, "team").toUpperCase());
                    Map map = Annihilation.getInstance().getMapManager().getMap(mapKey);
                    if (map != null) {
                        map.getTeamLocation(team).setEnderBrewer(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("success.map.set_ender_brewer", ImmutableMap.of("map", mapKey, "team", team.toString()))));
                    } else {
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("errors.not_found", ImmutableMap.of("map", mapKey))));
                    }
                }
            }
        }
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
                    event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("success.map.added_spawn", ImmutableMap.of("map", mapKey, "team", team.toString()))));
                } else {
                    event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("errors.not_found", ImmutableMap.of("map", mapKey))));
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
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("success.map.set_max_point")));
                    } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        map.getTeamLocation(team).getNexus().setMinCorner(event.getClickedBlock().getLocation());
                        event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("success.map.set_min_point")));
                    }
                } else {
                    event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("errors.not_found", ImmutableMap.of("map", mapKey))));
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
                            event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("success.map.added_diamond", ImmutableMap.of("map", mapKey))));
                        } else {
                            event.getPlayer().sendMessage(ChatWriter.write(Annihilation._l("errors.not_found", ImmutableMap.of("map", mapKey))));
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

}
