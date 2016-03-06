package me.mprey.util;

import me.mprey.Annihilation;
import me.mprey.map.MapManager;
import org.bukkit.*;
import org.bukkit.block.Block;
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
            List<String> ignore = Arrays.asList("uid.dat", "session.dat", MapManager.MAP_FILE);
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
        worldCreator.generator(new ChunkGenerator() {
            @Override
            public byte[] generate(World world, Random random, int x, int z) {
                return super.generate(world, random, x, z);
            }

            @Override
            public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
                return super.generateExtBlockSections(world, random, x, z, biomes);
            }

            @Override
            public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
                return super.generateBlockSections(world, random, x, z, biomes);
            }

            @Override
            public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
                return super.generateChunkData(world, random, x, z, biome);
            }

            @Override
            public boolean canSpawn(World world, int x, int z) {
                return true;
            }

            @Override
            public List<BlockPopulator> getDefaultPopulators(World world) {
                return Arrays.asList(new BlockPopulator[0]);
            }

            @Override
            public Location getFixedSpawnLocation(World world, Random random) {
                return new Location(world, 0.0D, 64.0D, 0.0D);
            }
        });

        World world = worldCreator.createWorld();
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(true, true);
        world.setPVP(!worldName.equals("lobby"));
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(1);
        world.setTicksPerMonsterSpawns(1);

        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");

        Block b = world.getBlockAt(0, 20, 0);
        b.setType(Material.STONE);

        return world;
    }

    public static boolean loadWorld(String worldName) {
        boolean isLobby = worldName.equalsIgnoreCase("lobby");

        WorldCreator creator = new WorldCreator(worldName);
        creator.generateStructures(false);
        creator.generator(new ChunkGenerator() {
            @Override
            public byte[] generate(World world, Random random, int x, int z) {
                return super.generate(world, random, x, z);
            }

            @Override
            public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
                return super.generateExtBlockSections(world, random, x, z, biomes);
            }

            @Override
            public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
                return super.generateBlockSections(world, random, x, z, biomes);
            }

            @Override
            public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
                return super.generateChunkData(world, random, x, z, biome);
            }

            @Override
            public boolean canSpawn(World world, int x, int z) {
                return true;
            }

            @Override
            public List<BlockPopulator> getDefaultPopulators(World world) {
                return Arrays.asList(new BlockPopulator[0]);
            }

            @Override
            public Location getFixedSpawnLocation(World world, Random random) {
                return new Location(world, 0.0D, 64.0D, 0.0D);
            }
        });

        World world = creator.createWorld();
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(true, true);
        world.setPVP(!isLobby);
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

        for (World world1 : Annihilation.getInstance().getServer().getWorlds()) {
            if (world1.getName().equals(world.getName())) {
                return true;
            }
        }
        return false;
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
            Annihilation.getInstance().getServer().unloadWorld(world, true);
        }
    }

}
