package me.mprey.shop;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class ShopManager {

    private FileConfiguration shopConfig;

    private ArrayList<Shop> shops;

    public ShopManager(FileConfiguration shopConfig) {
        this.shopConfig = shopConfig;
        this.shops = new ArrayList<>();
        init();
    }

    public void init() {
        //TODO load in various shops
    }

    public boolean isShop(Inventory inventory) {
        for (Shop shop : shops) {
            if (ChatColor.stripColor(inventory.getName()).equals(ChatColor.stripColor(shop.getName()))) {
                return true;
            }
        }
        return false;
    }

    public void handleInventoryClick(InventoryClickEvent event) {
        if (isShop(event.getClickedInventory()) && event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
        }
    }

}
