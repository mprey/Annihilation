package me.mprey.util;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class IconMenu implements Listener {

    private String name;
    private int size;
    private OptionClickEventHandler handler;
    private Plugin plugin;
    private Player player;

    private String[] optionNames;
    private ItemStack[] optionIcons;
    private boolean[] removeableIcons;

    public IconMenu(String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        this.removeableIcons = new boolean[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public IconMenu setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        removeableIcons[position] = false;
        return this;
    }

    public IconMenu setOption(int position, ItemStack icon) {
        optionNames[position] = icon.getItemMeta().getDisplayName();
        optionIcons[position] = icon;
        removeableIcons[position] = false;
        return this;
    }

    public IconMenu setRemoveableOption(int position, ItemStack icon) {
        optionNames[position] = icon.getItemMeta().getDisplayName();
        optionIcons[position] = icon;
        removeableIcons[position] = true;
        return this;
    }

    public void fillBlank(ItemStack filler) {
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] == null) {
                setOption(i, filler);
                optionNames[i] = "filler";
            }
        }
    }

    public void setSpecificTo(Player player) {
        this.player = player;
    }

    public boolean isSpecific() {
        return player != null;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionIcons = null;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals(name) && (player == null || event.getPlayer() == player)) {
            destroy();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name) && (player == null || event.getWhoClicked() == player)) {
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                event.setCancelled(true);
                Plugin plugin = this.plugin;
                if (removeableIcons[slot]) {
                    event.setCursor(optionIcons[slot]);
                }
                OptionClickEvent e = new OptionClickEvent((Player) event.getWhoClicked(), slot, optionNames[slot], optionIcons[slot]);
                handler.onOptionClick(e);
                ((Player) event.getWhoClicked()).updateInventory();
                if (e.willClose()) {
                    ((Player) event.getWhoClicked()).closeInventory();
                }
                if (e.willDestroy()) {
                    destroy();
                }
            }
        }
    }

    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);
    }

    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
        private ItemStack item;

        public OptionClickEvent(Player player, int position, String name, ItemStack item) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
            this.item = item;
        }

        public Player getPlayer() {
            return player;
        }

        public int getPosition() {
            return position;
        }

        public String getName() {
            return name;
        }

        public boolean willClose() {
            return close;
        }

        public boolean willDestroy() {
            return destroy;
        }

        public void setWillClose(boolean close) {
            this.close = close;
        }

        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }

        public ItemStack getItem() {
            return item;
        }
    }

    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

}