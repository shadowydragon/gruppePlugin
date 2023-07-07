package de.gruppe.plugin.cojoin.commands;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.cojoin.handlers.HealthAndHungerSyncHandlers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConjoinCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] arguments) {

        if (command.getName().equals("conjoin")) {
            if (arguments.length >= 1 && arguments.length < 3) {
                switch (arguments[0]) {
                    case "start" -> {
                        conjoinStart(commandSender);
                        System.out.println("Starten der Conjoin");
                        return true;
                    }
                    case "attack" -> {
                        if (arguments.length == 2) {
                            try {
                                Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                                attackController(commandSender, arguments, targetPlayer);
                            } catch (Exception e) {
                                System.out.println("Fehlerhaftes argument");
                                commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                                return false;
                            }
                        } else {
                            if (commandSender instanceof Player) {
                                attackController(commandSender, arguments, (Player) commandSender);
                                return true;
                            } else {
                                System.out.println("You aren't a player and can't get the Role");
                            }

                        }
                        return true;
                    }
                    case "break" -> {
                        if (arguments.length == 2) {
                            try {
                                Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                                breakController(commandSender, arguments, targetPlayer);
                            } catch (Exception e) {
                                System.out.println("Fehlerhaftes argument");
                                commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                                return false;
                            }
                        } else {
                            if (commandSender instanceof Player) {
                                breakController(commandSender, arguments, (Player) commandSender);
                                return true;
                            } else {
                                System.out.println("You aren't a player and can't get the Role");
                            }

                        }
                        return true;
                    }
                    case "inventory" -> {
                        if (arguments.length == 2) {
                            try {
                                Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                                inventoryController(commandSender, arguments, targetPlayer);
                            } catch (Exception e) {
                                System.out.println("Fehlerhaftes argument");
                                commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                                return false;
                            }
                        } else {
                            if (commandSender instanceof Player) {
                                inventoryController(commandSender, arguments, (Player) commandSender);
                                return true;
                            } else {
                                System.out.println("You aren't a player and can't get the Role");
                            }

                        }
                        return true;
                    }
                    case "movement" -> {
                        if (arguments.length == 2) {
                            try {
                                Player targetPlayer = Bukkit.getPlayer(arguments[1]);

                                movementController(commandSender, arguments, targetPlayer);
                            } catch (Exception e) {
                                System.out.println("Fehlerhaftes argument");
                                commandSender.sendMessage(ChatColor.RED + "Ungültiges argument versuche:\n/conjoin attack <Spielername>");
                                return false;
                            }
                        } else {
                            if (commandSender instanceof Player) {
                                movementController(commandSender, arguments, (Player) commandSender);
                                return true;
                            } else {
                                System.out.println("You aren't a player and can't get the Role");
                            }

                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void conjoinStart(CommandSender commandSender) {
        if (Main.checkControllers()) {
            Main.isConjoined = true;

            try {
                Player attackController = Bukkit.getPlayer(Main.attackController);
                Player breakController = Bukkit.getPlayer(Main.breakController);
                Player inventoryController = Bukkit.getPlayer(Main.inventoryController);
                Player movementController = Bukkit.getPlayer(Main.movementController);

                Main plugin = Main.plugin;
                if (attackController != null && breakController != null && inventoryController != null && movementController != null) {
                    attackController.hidePlayer(plugin, attackController);
                    attackController.hidePlayer(plugin, breakController);
                    attackController.hidePlayer(plugin, inventoryController);
                    attackController.hidePlayer(plugin, movementController);
                    attackController.setCollidable(false);
                    attackController.setCanPickupItems(false);
                    attackController.setInvulnerable(true);
                    attackController.setInvisible(true);

                    breakController.hidePlayer(plugin, attackController);
                    breakController.hidePlayer(plugin, breakController);
                    breakController.hidePlayer(plugin, inventoryController);
                    breakController.hidePlayer(plugin, movementController);
                    breakController.setCollidable(false);
                    breakController.setCanPickupItems(false);
                    breakController.setInvisible(true);
                    breakController.setInvulnerable(true);

                    inventoryController.hidePlayer(plugin, attackController);
                    inventoryController.hidePlayer(plugin, breakController);
                    inventoryController.hidePlayer(plugin, inventoryController);
                    inventoryController.hidePlayer(plugin, movementController);

                    inventoryController.setInvulnerable(true);
                    inventoryController.setInvisible(true);

                    movementController.hidePlayer(plugin, attackController);
                    movementController.hidePlayer(plugin, breakController);
                    movementController.hidePlayer(plugin, inventoryController);
                    //movementController.hidePlayer(plugin, movementController);
                    movementController.setCollidable(true);
                    movementController.setCanPickupItems(false);

                    inventoryController.setCollidable(false);
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            HealthAndHungerSyncHandlers.syncHunger();
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cBoth Move and Interact controllers must be assigned before conjoining!"));
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &f/movement <player_name> &eand &f/interact <player_name> &eto assign controllers."));
        }
    }

    private void attackController(CommandSender commandSender, String[] strings, Player player) {
        if (player != null) {
            Main.attackController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() + " &eas the &fattack &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0])) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &fattack &econtroller."));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

    private void breakController(CommandSender commandSender, String[] strings, Player player) {
        if (player != null) {
            Main.breakController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() + " &eas the &fbreak &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0])) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &fbreak &econtroller."));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

    private void inventoryController(CommandSender commandSender, String[] strings, Player player) {
        if (player != null) {
            Main.inventoryController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() + " &eas the &finventory &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0])) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &finventory &econtroller."));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

    private void movementController(CommandSender commandSender, String[] strings, Player player) {
        if (player != null) {
            Main.movementController = player.getUniqueId();

            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() + " &eas the &fmovement &econtroller."));

            if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0])) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &fmovement &econtroller."));
            }
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
        }
    }

}
