package de.gruppe.plugin;

import de.gruppe.plugin.cojoin.handlers.*;
import de.gruppe.plugin.commands.*;
import de.gruppe.plugin.cojoin.commands.*;
import de.gruppe.plugin.handlers.ChatEvents;
import de.gruppe.plugin.manhunt.commands.ManhuntCommand;
import de.gruppe.plugin.manhunt.handlers.CompassHandler;
import de.gruppe.plugin.manhunt.handlers.ManhuntPlayerHandler;
import de.gruppe.plugin.manhunt.listener.MenuListener;
import de.gruppe.plugin.menusystem.PlayerMenuUtility;
import de.gruppe.plugin.multiverse.command.MultiverseCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.HashMap;
import java.util.Objects;
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

        //new CoJoinSaveConfig();

        //getConfig().options().copyDefaults();
        //saveDefaultConfig();

        registerCommands();
        registerHandlers();

    }

    private void registerCommands() {
        if (getConfig().getBoolean("Conjoin"))
        {
            Objects.requireNonNull(getCommand("cojoin")).setExecutor(new CoJoinCommand());
        }

        Objects.requireNonNull(getCommand("setName")).setExecutor(new SetNameCommand());
        Objects.requireNonNull(getCommand("Collidable")).setExecutor(new CollidableCommand());
        Objects.requireNonNull(getCommand("HideFromPlayer")).setExecutor(new HideFromPlayerCommand());
        Objects.requireNonNull(getCommand("ShowToPlayer")).setExecutor(new ShowToPlayerCommand());
        Objects.requireNonNull(getCommand("Vanish")).setExecutor(new VanishCommand());
        Objects.requireNonNull(getCommand("God")).setExecutor(new GodCommand());
        Objects.requireNonNull(getCommand("Fly")).setExecutor(new FlyCommand());
        Objects.requireNonNull(getCommand("menu")).setExecutor(new OpenMainMenuCommand());
        Objects.requireNonNull(getCommand("EnderChest")).setExecutor(new EnderChestCommand());
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new InvSeeCommand());


        if (getConfig().getBoolean("Manhunt"))
        {
            Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommand());
        }


        if(getConfig().getBoolean("Multiverse"))
        {
            Objects.requireNonNull(getCommand("multiverse")).setExecutor(new MultiverseCommand());
        }
    }

    private void registerHandlers() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ChatEvents(), this);
        System.out.println("registered\n\n\n");

        if (getConfig().getBoolean("Conjoin"))
        {
            //pluginManager.registerEvents(new CojoinedHandlers(), this);
            //pluginManager.registerEvents(new InventorySyncHandlers(), this);
            //pluginManager.registerEvents(new HealthAndHungerSyncHandlers(), this);

            pluginManager.registerEvents(new CoJoinInventoryHandler(), this);
            pluginManager.registerEvents(new CoJoinMovementHandler(), this);
            pluginManager.registerEvents(new CoJoinActionsHandler(), this);
            pluginManager.registerEvents(new CoJoinSyncHandler(),this);
            pluginManager.registerEvents(new CoJoinPlayerHandler(),this);
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
