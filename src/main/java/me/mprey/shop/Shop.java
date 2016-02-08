package me.mprey.shop;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class Shop {

    private Inventory inventory;

    private String name;

    private ArrayList<ShopItem> items;

    public Shop(String name, ArrayList<ShopItem> items) {
        this.name = name;
        this.items = items;
        this.inventory = Bukkit.createInventory(null, getSize(), name);
        populateInventory();
    }

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void populateInventory() {
        for (ShopItem item : items) {
            inventory.setItem(item.getInventorySpot(), item.getItemStack());
        }
    }

    public int getSize() {
        int maxIndex = 0;
        for (ShopItem item : items) {
            if (item.getInventorySpot() > maxIndex) {
                maxIndex = item.getInventorySpot();
            }
        }
        if (maxIndex <= 0) {
            return 9;
        } else {
            int quotient = (int) Math.ceil(maxIndex / 9.0);
            return quotient > 5 ? 54 : quotient * 9;
        }
    }

}
