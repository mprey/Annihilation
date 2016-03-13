package me.mprey.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 * Created by Mason Prey on 3/6/16.
 */
public class VoidGenerator extends ChunkGenerator {

    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        return new byte[32768];
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        return null;
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        return null;
    }

    @Override
    public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
        return null;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, world.getHighestBlockYAt(0, 0), 0);
    }
}
