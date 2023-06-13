package de.gruppe.plugin;

import de.gruppe.plugin.commands.SetName;
import de.gruppe.plugin.conjoin.commands.*;
import de.gruppe.plugin.conjoin.handlers.ConjoinedHandlers;
import de.gruppe.plugin.conjoin.handlers.HealthAndHungerSyncHandlers;
import de.gruppe.plugin.conjoin.handlers.InventorySyncHandlers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



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

        registerCommands();
        registerHandlers();
    }

    private void registerCommands() {
        getCommand("conjoin").setExecutor(new ConjoinCommand());
        getCommand("setName").setExecutor(new SetName());

    }

    private void registerHandlers() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ConjoinedHandlers(), this);
        pluginManager.registerEvents(new InventorySyncHandlers(), this);
        pluginManager.registerEvents(new HealthAndHungerSyncHandlers(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
