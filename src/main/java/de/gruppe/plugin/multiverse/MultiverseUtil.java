package de.gruppe.plugin.multiverse;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class MultiverseUtil
{
    public static World generateNewWorld(String worldName)
    {
        WorldCreator   worldCreator = new WorldCreator(worldName);
        return worldCreator.createWorld();
    }

    public static boolean woldExist(String worldName)
    {
        for (World world : Bukkit.getServer().getWorlds()) {
            if (world.getName().equalsIgnoreCase(worldName))
            {
                return true;
            }
        }
        return false;
    }

    public static World getWorldFromString(String worldName)
    {
        for (World world : Bukkit.getServer().getWorlds()) {
            if (world.getName().equalsIgnoreCase(worldName))
            {
                return world;
            }
        }
        return null;
    }

    public static boolean woldExist(World world)
    {
        if (Bukkit.getServer().getWorlds().contains(world))
        {
            return true;
        }
        return false;
    }
}
