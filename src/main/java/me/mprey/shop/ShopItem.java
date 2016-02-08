package me.mprey.shop;

import me.mprey.util.ItemUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mason Prey on 2/8/16.
 */
public class ShopItem {

    private Material material;

    private int inventorySpot, quantity, price;

    private ItemStack itemStack;

    public ShopItem(Material material, int inventorySpot, int quantity, int price) {
        this.material = material;
        this.inventorySpot = inventorySpot;
        this.quantity = quantity;
        this.price = price;
    }

    public int getInventorySpot() {
        return inventorySpot;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void addToInventory(Inventory inventory) {
        if (!(this.inventorySpot >= 0)) {
            this.inventorySpot = 0;
        }
        inventory.setItem(inventorySpot, getItemStack());
    }

    public ItemStack getItemStack() {
        if (itemStack == null) {
            itemStack = ItemUtil.nameAndLore(material, material.toString(), getLore());
        }
        return itemStack;
    }

    public ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Quantity: " + quantity);
        lore.add("");
        lore.add(ChatColor.GOLD + "Price: " + price + " gold"); //TODO switch lore into locale
        return lore;
    }

}
