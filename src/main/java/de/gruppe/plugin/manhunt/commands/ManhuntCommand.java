package de.gruppe.plugin.manhunt.commands;

import de.gruppe.plugin.manhunt.handlers.CompassHandler;
import de.gruppe.plugin.manhunt.items.TargetCompass;
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
            System.out.println("1");
            if (args.length > 0)
            {
                System.out.println("2");
                if (args[0].equals("getcompass"))
                {
                    System.out.println("3");
                    if (sender instanceof Player)
                    {
                        System.out.println("4");
                        Player player = (Player) sender;

                        if (player.getInventory().getItem(9) == null)
                        {
                            System.out.println("5");
                            player.getInventory().setItem(9, TargetCompass.createCreate());
                            System.out.println("Klappt");
                            return true;
                        }

                        if (player.getInventory().firstEmpty() == -1)
                        {
                            System.out.println("6");
                            player.sendMessage("Du hast keinen freien slot");
                            return true;
                        }else
                        {
                            player.getInventory().addItem(TargetCompass.createCreate());
                        }

                    }
                } else if (args[0].equals("settarget"))
                {
                    System.out.println("7");
                    if (args.length > 1)
                    {
                        System.out.println("8");
                        try {
                            Player targetPlayer = Bukkit.getPlayer(args[1]);
                            Player sendPlayer = (Player) sender;

                            sendPlayer.setCompassTarget(targetPlayer.getLocation());
                            sendPlayer.sendMessage("Target was set to " + targetPlayer.getName());
                            CompassHandler.addUserTarget(sendPlayer.getUniqueId(), targetPlayer.getUniqueId());
                            System.out.println("9");


                        }
                        catch (Exception e)
                        {
                            sender.sendMessage("Dieser spieler exestiert nicht");
                        }
                    }
                }
            }
        }

        return false;
    }
}
