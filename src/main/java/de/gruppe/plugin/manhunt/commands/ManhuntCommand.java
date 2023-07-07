package de.gruppe.plugin.manhunt.commands;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.manhunt.ManhuntRoles;
import de.gruppe.plugin.manhunt.handlers.CompassHandler;
import de.gruppe.plugin.manhunt.items.TargetCompass;
import de.gruppe.plugin.manhunt.menu.ManhuntMainMenu;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ManhuntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("manhunt")) {
            if (sender instanceof Player player) {

                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("getCompass")) {

                        if (player.getInventory().getItem(8) == null) {
                            player.getInventory().setItem(8, TargetCompass.createCreate());
                            System.out.println("Klappt");
                            return true;
                        }

                        if (player.getInventory().firstEmpty() == -1) {
                            player.sendMessage("Du hast keinen freien Slot.");
                            return true;
                        } else {
                            player.getInventory().addItem(TargetCompass.createCreate());
                        }

                    } else if (args[0].equals("setTarget")) {
                        if (args.length > 1) {
                            try {
                                Player targetPlayer = Bukkit.getPlayer(args[1]);
                                Player sendPlayer = (Player) sender;

                                sendPlayer.setCompassTarget(Objects.requireNonNull(targetPlayer).getLocation());
                                sendPlayer.sendMessage("Target was set to " + targetPlayer.getName() + ".");
                                CompassHandler.addUserTarget(sendPlayer.getUniqueId(), targetPlayer.getUniqueId());

                            } catch (Exception e) {
                                sender.sendMessage("Dieser spieler existiert nicht.");
                            }
                        }
                    } else if (args[0].equalsIgnoreCase("menu")) {
                        ManhuntMainMenu manhuntMainMenu = new ManhuntMainMenu(Main.getPlayerMenuUtility(player));

                        manhuntMainMenu.open(player);
                    } else if (args[0].equalsIgnoreCase("setRole")) {

                        if (args[1].equalsIgnoreCase(ManhuntRoles.NONE.toString())) {
                            System.out.println(player.getUniqueId());

                            player.getPersistentDataContainer().set(new NamespacedKey(player.getUniqueId().toString(), ManhuntRoles.MANHUNTROLLE.name().toLowerCase()), PersistentDataType.STRING, ManhuntRoles.NONE.toString());
                        } else if (args[1].equalsIgnoreCase(ManhuntRoles.HUNTER.toString())) {

                            player.getPersistentDataContainer().set(new NamespacedKey(player.getUniqueId().toString(), ManhuntRoles.MANHUNTROLLE.name().toLowerCase()), PersistentDataType.STRING, ManhuntRoles.HUNTER.toString());
                        } else if (args[1].equalsIgnoreCase(ManhuntRoles.SPEEDRUNNER.toString())) {
                            player.getPersistentDataContainer().set(new NamespacedKey(player.getUniqueId().toString(), ManhuntRoles.MANHUNTROLLE.name().toLowerCase()), PersistentDataType.STRING, ManhuntRoles.SPEEDRUNNER.toString());
                        }
                    }
                }
            }

        }

        return false;
    }
}
