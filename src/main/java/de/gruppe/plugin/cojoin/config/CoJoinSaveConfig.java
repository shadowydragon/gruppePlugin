package de.gruppe.plugin.cojoin.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CoJoinSaveConfig {

    private static File file;
    private static YamlConfiguration config;

    public CoJoinSaveConfig() {

        File dir = new File("./plugins/Saves/");
        if (!dir.exists())
        {
            dir.mkdirs();
        }

        file = new File(dir, "cojoinSaves.yml");
        if (!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static boolean contains(String paths)
    {
        return config.contains(paths);
    }

    public static void set(String path, Object value)
    {
        config.set(path, value);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object get(String path)
    {
        if (!contains(path))
        {
            return null;
        }
        else
        {
            return config.get(path);
        }
    }
}
