package de.gruppe.plugin.manhunt.commands;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.manhunt.handlers.CompassHandler;
import de.gruppe.plugin.manhunt.items.TargetCompass;
import de.gruppe.plugin.manhunt.menusystem.menu.ManhuntMainMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ManhuntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("manhunt"))
        {
            if (sender instanceof Player)
            {
                Player player = (Player) sender;

                if (args.length > 0)
                {
                    if (args[0].equalsIgnoreCase("getcompass"))
                    {



                        if (player.getInventory().getItem(8) == null)
                        {
                            player.getInventory().setItem(8, TargetCompass.createCreate());
                            System.out.println("Klappt");
                            return true;
                        }

                        if (player.getInventory().firstEmpty() == -1)
                        {
                            player.sendMessage("Du hast keinen freien slot");
                            return true;
                        }else
                        {
                            player.getInventory().addItem(TargetCompass.createCreate());
                        }


                    } else if (args[0].equals("settarget"))
                    {
                        if (args.length > 1)
                        {
                            try {
                                Player targetPlayer = Bukkit.getPlayer(args[1]);
                                Player sendPlayer = (Player) sender;

                                sendPlayer.setCompassTarget(targetPlayer.getLocation());
                                sendPlayer.sendMessage("Target was set to " + targetPlayer.getName());
                                CompassHandler.addUserTarget(sendPlayer.getUniqueId(), targetPlayer.getUniqueId());


                            }
                            catch (Exception e)
                            {
                                sender.sendMessage("Dieser spieler exestiert nicht");
                            }
                        }
                    }
                    else if (args[0].equalsIgnoreCase("menu"))
                    {
                        ManhuntMainMenu manhuntMainMenu = new ManhuntMainMenu(Main.getPlayerMenuUtility(player));

                        manhuntMainMenu.open();
                    }
                }
            }

        }

        return false;
    }
}
