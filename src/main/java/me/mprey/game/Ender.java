package me.mprey.game;

import me.mprey.Annihilation;
import net.minecraft.server.v1_9_R1.Blocks;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.TileEntityBrewingStand;
import net.minecraft.server.v1_9_R1.TileEntityFurnace;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftInventoryBrewer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mason Prey on 2/27/16.
 */
public class Ender {

    public enum EnderType  {
        FURNACE(EnderFurnace.class),
        BREWER(EnderBrewer.class),
        CHEST(EnderChest.class);

        private Class c;

        EnderType(Class c) {
            this.c = c;
        }

        public Class getAssociatedClass() {
            return this.c;
        }

        public ItemStack getBlock() {
            switch (this) {
                case FURNACE:
                    return new ItemStack(Material.FURNACE);
                case BREWER:
                    return new ItemStack(Material.BREWING_STAND_ITEM);
                case CHEST:
                    return new ItemStack(Material.CHEST);
                default:
                    return null;
            }
        }

        public String getInfo() {
            return Annihilation._l("game.ender." + this.name().toLowerCase() + ".info_msg");
        }

        public String toString() {
            return Annihilation._l("game.ender." + this.name().toLowerCase() + ".name");
        }
    }

    private static Map<Player, List<EnderItem>> map = new HashMap<>();

    public static void openEnder(Player player, EnderType ender) {
        if (map.containsKey(player)) {
            for (EnderItem item : map.get(player)) {
                if (item.getClass().getName().equals(ender.getAssociatedClass().getName())) {
                    item.open();
                    return;
                }
            }
        } else {
            map.put(player, new ArrayList<EnderItem>());
        }
        EnderItem item = null;
        switch (ender) {
            case FURNACE:
                item = new EnderFurnace(((CraftPlayer) player).getHandle());
                break;
            case BREWER:
                item = new EnderBrewer(((CraftPlayer) player).getHandle());
                break;
            case CHEST:
                item = new EnderChest(player);
                break;
        }
        map.get(player).add(item);
        player.sendMessage(ender.getInfo());
        item.open();
    }

    public static void clear(Player player) {
        if (map.containsKey(player)) {
            map.remove(player);
        }
    }

    public Ender(Annihilation instance) {
        Bukkit.getScheduler().runTaskTimer(instance, new Runnable() {
            @Override
            public void run() {
                for (List<EnderItem> items : map.values()) {
                    for (EnderItem item : items) {
                        if (item instanceof EnderFurnace) {
                            try {
                                ((EnderFurnace) item).c();
                            } catch (Exception ignored) {}
                        } else if (item instanceof EnderBrewer) {
                            try {
                                ((EnderBrewer) item).c();
                            } catch (Exception ignored) {}
                        }
                    }
                }
            }
        }, 0L, 1L);
    }

    public interface EnderItem {
        void open();
    }

    private static class EnderChest implements EnderItem {

        private Inventory inventory;

        private Player player;

        public EnderChest(Player player) {
            this.player = player;
            this.inventory = Bukkit.createInventory(player, InventoryType.CHEST);
        }

        public void open() {
            this.player.openInventory(inventory);
        }

    }

    private static class EnderFurnace extends TileEntityFurnace implements EnderItem {

        private EntityHuman human;

        public EnderFurnace(EntityHuman human) {
            world = human.world;
            this.human = human;
        }

        public boolean a(EntityHuman human) {
            return true;
        }

        public int p() {
            return 0;
        }

        public void update() {}

        public InventoryHolder getOwner() {
            return new InventoryHolder() {
                @Override
                public Inventory getInventory() {
                    return new CraftInventoryBrewer(EnderFurnace.this);
                }
            };
        }

        public void open() {
            human.openContainer(this);
        }
    }

    private static class EnderBrewer extends TileEntityBrewingStand implements EnderItem {

        private EntityHuman human;

        public EnderBrewer(EntityHuman human) {
            world = human.world;
            this.human = human;
        }

        public boolean a(EntityHuman human) {
            return true;
        }

        public int p() {
            return 0;
        }

        public void update() {}

        public InventoryHolder getOwner() {
            return new InventoryHolder() {
                @Override
                public Inventory getInventory() {
                    return new CraftInventoryBrewer(EnderBrewer.this);
                }
            };
        }

        public void open() {
            human.openContainer(this);
        }

    }

}
