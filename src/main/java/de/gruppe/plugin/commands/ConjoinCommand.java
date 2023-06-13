package de.gruppe.plugin.commands;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.handlers.HealthAndHungerSyncHandlers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConjoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {

        if(arguments.length == 0)
        {
            if (Main.checkControlers())
            {
                Main.isConjoined = true;

                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.setBedSpawnLocation(player.getLocation(), true);
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                    player.setCollidable(false);
                }

                try {
                    Player attackControler = Bukkit.getPlayer(Main.attackController);
                    Player breakControler = Bukkit.getPlayer(Main.breakController);
                    Player inventoryControler = Bukkit.getPlayer(Main.inventoryController);
                    Player movementControler = Bukkit.getPlayer(Main.movementController);

                    Main plugin = Main.plugin;
                    attackControler.hidePlayer(plugin, attackControler);
                    attackControler.hidePlayer(plugin, breakControler);
                    attackControler.hidePlayer(plugin, inventoryControler);
                    attackControler.hidePlayer(plugin, movementControler);
                    attackControler.setCollidable(false);


                    breakControler.hidePlayer(plugin, attackControler);
                    breakControler.hidePlayer(plugin, breakControler);
                    breakControler.hidePlayer(plugin, inventoryControler);
                    breakControler.hidePlayer(plugin, movementControler);
                    breakControler.setCollidable(false);

                    inventoryControler.hidePlayer(plugin, attackControler);
                    inventoryControler.hidePlayer(plugin, breakControler);
                    inventoryControler.hidePlayer(plugin, inventoryControler);
                    inventoryControler.hidePlayer(plugin, movementControler);
                    inventoryControler.setCollidable(false);

                    movementControler.hidePlayer(plugin, attackControler);
                    movementControler.hidePlayer(plugin, breakControler);
                    movementControler.hidePlayer(plugin, inventoryControler);
                    movementControler.hidePlayer(plugin, movementControler);
                    movementControler.setCollidable(true);
                }catch (Exception e)
                {
                    System.out.println(e);
                }


                HealthAndHungerSyncHandlers.syncHunger();
            }
            else
            {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cBoth Move and Interact controllers must be assigned before conjoining!"));
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &f/movement <player_name> &eand &f/interact <player_name> &eto assign controllers."));
            }
        }
        else
        {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cInvalid arguments!"));
        }
        return false;
    }
}
