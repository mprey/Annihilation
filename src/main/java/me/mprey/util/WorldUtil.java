package me.mprey.util;

import me.mprey.Annihilation;
import me.mprey.map.MapManager;
import org.bukkit.World;

import java.io.*;
import java.util.Arrays;
import java.util.List;

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

    public static boolean deleteWorld(File file) {
        if (file != null && file.exists()) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    deleteWorld(f);
                } else {
                    f.delete();
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
