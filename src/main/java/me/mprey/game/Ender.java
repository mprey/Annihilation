package me.mprey.game;

import me.mprey.Annihilation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/27/16.
 */
public class Ender {

    public enum EnderType  {
        FURNACE(InventoryType.FURNACE),
        BREWER(InventoryType.BREWING),
        CHEST(InventoryType.CHEST);

        private InventoryType inventoryType;

        EnderType(InventoryType inventoryType) {
            this.inventoryType = inventoryType;
        }

        public InventoryType getInventoryType() {
            return this.inventoryType;
        }

        public ItemStack getBlock() {
            switch (this) {
                case FURNACE:
                    return new ItemStack(Material.BURNING_FURNACE);
                case BREWER:
                    return new ItemStack(Material.BREWING_STAND);
                case CHEST:
                    return new ItemStack(Material.CHEST);
                default:
                    return null;
            }
        }

        public String toString() {
            return Annihilation._l("game.ender." + this.name().toLowerCase());
        }
    }

    private static Map<Player, Map<EnderType, Inventory>> map = new HashMap<>();

    public static void openEnder(Player player, EnderType ender) {
        Inventory inv = Bukkit.createInventory(player, ender.getInventoryType());
        if (map.containsKey(player)) {
            Map<EnderType, Inventory> playerMap = map.get(player);
            if (!playerMap.containsKey(ender)) {
                playerMap.put(ender, inv);
                map.put(player, playerMap);
            }
            player.openInventory(playerMap.get(ender));
        } else {
            Map<EnderType, Inventory> playerMap = new HashMap<>();
            playerMap.put(ender, inv);
            map.put(player, playerMap);
            player.openInventory(playerMap.get(ender));
        }
    }

}
