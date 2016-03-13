package me.mprey.util;

import me.mprey.Annihilation;
import me.mprey.map.Lobby;
import me.mprey.map.MapManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Mason Prey on 2/23/16.
 */
public class WorldUtil {

    public static void copyWorld(File source, File target) {
        try {
            List<String> ignore = Arrays.asList("uid.dat", "session.dat", "playerdata", MapManager.LOBBY_FILE, MapManager.MAP_FILE);
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists()) target.mkdirs();
                    for (String f : source.list()) {
                        copyWorld(new File(source, f), new File(target, f));
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWorld(String worldName) {
        unloadWorld(worldName);
        deleteWorld(new File(Annihilation.getInstance().getServer().getWorldContainer().getAbsolutePath(), worldName));
    }

    public static World createEmptyWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generateStructures(false);
        worldCreator.generator(new VoidGenerator());
        World world = prepareWorld(worldCreator.createWorld(), !worldName.equals(Lobby.FILE_NAME));

        Block b = world.getBlockAt(0, 20, 0);
        b.setType(Material.STONE);

        world.setSpawnLocation(0, 22, 0);

        return world;
    }

    public static boolean loadWorld(String worldName) {
        boolean isLobby = worldName.equalsIgnoreCase(Lobby.FILE_NAME);

        WorldCreator creator = new WorldCreator(worldName);
        creator.generateStructures(false);
        creator.generator(new VoidGenerator());
        World world = prepareWorld(creator.createWorld(), !isLobby);

        for (World world1 : Annihilation.getInstance().getServer().getWorlds()) {
            if (world1.getName().equals(world.getName())) {
                return true;
            }
        }
        return false;
    }

    private static World prepareWorld(World world, boolean pvp) {
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(true, true);
        world.setPVP(pvp);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setStorm(false);
        world.setThundering(false);
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);

        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");

        return world;
    }

    public static boolean deleteWorld(File file) {
        if (file != null && file.exists()) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    deleteWorld(f);
                } else {
                    if (!f.getName().contains(".yml")) {
                        f.delete();
                    }
                }
            }
        }
        return file.delete();
    }

    public static void unloadWorld(String worldName) {
        World world = Annihilation.getInstance().getServer().getWorld(worldName);
        if (world != null) {
            for (Player player : world.getPlayers()) {
                player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            }
            Annihilation.getInstance().getServer().unloadWorld(world, true);
        }
    }

}
