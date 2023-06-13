package de.gruppe.plugin.conjoin.commands;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.conjoin.handlers.HealthAndHungerSyncHandlers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConjoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {

        if (command.getName().equals("conjoin"))
        {
            if (arguments.length >=1 && arguments.length < 3)
            {
                if (arguments[0].equals("start"))
                {
                    conjoinStart(commandSender);
                    System.out.println("Starten der Conjoin");
                    return true;
                } else if (arguments[0].equals("attack"))
                {
                    if (arguments.length == 2)
                    {
                        try {
                            Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                            attackController(commandSender, arguments, targetPlayer);
                        }catch (Exception e)
                        {
                            System.out.println("Fehlerhaftes argumetn");
                            commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                            return false;
                        }
                    } else
                    {
                        if (commandSender instanceof Player)
                        {
                            attackController(commandSender, arguments,(Player) commandSender);
                            return true;
                        }
                        else
                        {
                            System.out.println("You aren't a player and can't get the Role");
                        }


                    }

                    return true;
                }
                else if (arguments[0].equals("break"))
                {
                    if (arguments.length == 2)
                    {
                        try {
                            Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                            breakController(commandSender, arguments, targetPlayer);
                        }catch (Exception e)
                        {
                            System.out.println("Fehlerhaftes argumetn");
                            commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                            return false;
                        }
                    } else
                    {
                        if (commandSender instanceof Player)
                        {
                            breakController(commandSender, arguments,(Player) commandSender);
                            return true;
                        }
                        else
                        {
                            System.out.println("You aren't a player and can't get the Role");
                        }


                    }

                    return true;
                }
                else if (arguments[0].equals("inventory"))
                {
                    if (arguments.length == 2)
                    {
                        try {
                            Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                            inventoryController(commandSender, arguments, targetPlayer);
                        }catch (Exception e)
                        {
                            System.out.println("Fehlerhaftes argumetn");
                            commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                            return false;
                        }
                    } else
                    {
                        if (commandSender instanceof Player)
                        {
                            inventoryController(commandSender, arguments,(Player) commandSender);
                            return true;
                        }
                        else
                        {
                            System.out.println("You aren't a player and can't get the Role");
                        }


                    }

                    return true;
                }
                else if (arguments[0].equals("movement"))
                {
                    if (arguments.length == 2)
                    {
                        try {
                            Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                            movementController(commandSender, arguments, targetPlayer);
                        }catch (Exception e)
                        {
                            System.out.println("Fehlerhaftes argumetn");
                            commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                            return false;
                        }
                    } else
                    {
                        if (commandSender instanceof Player)
                        {
                            movementController(commandSender, arguments,(Player) commandSender);
                            return true;
                        }
                        else
                        {
                            System.out.println("You aren't a player and can't get the Role");
                        }


                    }

                    return true;
                }
            }
        }
        return false;
    }

    private void conjoinStart(CommandSender commandSender)
    {
        if (Main.checkControlers())
        {
            Main.isConjoined = true;

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
                attackControler.setCanPickupItems(false);
                attackControler.setInvulnerable(true);
                attackControler.setInvisible(true);




                breakControler.hidePlayer(plugin, attackControler);
                breakControler.hidePlayer(plugin, breakControler);
                breakControler.hidePlayer(plugin, inventoryControler);
                breakControler.hidePlayer(plugin, movementControler);
                breakControler.setCollidable(false);
                breakControler.setCanPickupItems(false);
                breakControler.setInvisible(true);
                breakControler.setInvulnerable(true);

                inventoryControler.hidePlayer(plugin, attackControler);
                inventoryControler.hidePlayer(plugin, breakControler);
                inventoryControler.hidePlayer(plugin, inventoryControler);
                inventoryControler.hidePlayer(plugin, movementControler);

                inventoryControler.setInvulnerable(true);
                inventoryControler.setInvisible(true);


                movementControler.hidePlayer(plugin, attackControler);
                movementControler.hidePlayer(plugin, breakControler);
                movementControler.hidePlayer(plugin, inventoryControler);
                //movementControler.hidePlayer(plugin, movementControler);
                movementControler.setCollidable(true);
                movementControler.setCanPickupItems(false);

                inventoryControler.setCollidable(false);
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




    private void attackController(CommandSender commandSender,String[] strings, Player player)
    {
        if (player != null)
        {
            Main.attackController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() +
                    " &eas the &fattack &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0]))
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &fattack &econtroller."));
            }
        }
        else
        {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

    private void breakController(CommandSender commandSender, String[] strings, Player player)
    {
        if (player != null)
        {
            Main.breakController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() +
                    " &eas the &fbreak &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0]))
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &fbreak &econtroller."));
            }
        }
        else
        {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

    private void inventoryController(CommandSender commandSender, String[] strings, Player player)
    {
        if (player != null)
        {
            Main.inventoryController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() +
                    " &eas the &finventory &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0]))
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &finventory &econtroller."));
            }
        }
        else
        {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

    private void movementController(CommandSender commandSender, String[] strings, Player player)
    {
        if (player != null)
        {
            Main.movementController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() +
                    " &eas the &fmovement &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0]))
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &fmovement &econtroller."));
            }
        }
        else
        {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

}
