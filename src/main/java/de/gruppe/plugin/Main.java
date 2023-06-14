package de.gruppe.plugin;

import de.gruppe.plugin.commands.SetName;
import de.gruppe.plugin.conjoin.commands.*;
import de.gruppe.plugin.conjoin.handlers.ConjoinedHandlers;
import de.gruppe.plugin.conjoin.handlers.HealthAndHungerSyncHandlers;
import de.gruppe.plugin.conjoin.handlers.InventorySyncHandlers;
import de.gruppe.plugin.manhunt.commands.ManhuntCommand;
import de.gruppe.plugin.manhunt.handlers.CompassHandler;
import de.gruppe.plugin.manhunt.handlers.ManhuntPlayerHandler;
import de.gruppe.plugin.manhunt.listener.MenuListener;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.HashMap;
import java.util.UUID;


public final class Main extends JavaPlugin {
    public static UUID attackController;
    public static UUID inventoryController;
    public static UUID movementController;
    public static UUID breakController;

    public static Main plugin;
    public static boolean isConjoined = false;

    public static boolean checkControlers()
    {
        return ((attackController != null) && (inventoryController != null) && (movementController != null) && (breakController != null));
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        System.out.println("Ich habe gestartet");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        registerCommands();
        registerHandlers();

    }

    private void registerCommands() {
        if (getConfig().getBoolean("Conjoin"))
        {
            getCommand("conjoin").setExecutor(new ConjoinCommand());
        }

        getCommand("setName").setExecutor(new SetName());

        if (getConfig().getBoolean("Manhunt"))
        {
            getCommand("manhunt").setExecutor(new ManhuntCommand());
        }



    }

    private void registerHandlers() {
        PluginManager pluginManager = Bukkit.getPluginManager();

        if (getConfig().getBoolean("Conjoin"))
        {
            pluginManager.registerEvents(new ConjoinedHandlers(), this);
            pluginManager.registerEvents(new InventorySyncHandlers(), this);
            pluginManager.registerEvents(new HealthAndHungerSyncHandlers(), this);
        }


        if (getConfig().getBoolean("Manhunt"))
        {
            pluginManager.registerEvents(new MenuListener(), this);
            pluginManager.registerEvents(new CompassHandler(), this);
            pluginManager.registerEvents(new ManhuntPlayerHandler(), this);
        }


    }

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>(); //first is the key second the value

    public static PlayerMenuUtility getPlayerMenuUtility(Player player)
    {
        PlayerMenuUtility playerMenuUtility;

        if (playerMenuUtilityMap.containsKey(player))
        {
            return playerMenuUtilityMap.get(player);
        }
        else
        {
            playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, playerMenuUtility);

            return playerMenuUtility;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
