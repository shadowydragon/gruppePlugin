package de.gruppe.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetName implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equals("setName"))
        {
            if (args.length == 0)
            {
                sender.sendMessage(ChatColor.RED + "Invalid Arguments");
            } else if (args.length == 1)
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    //player.setDisplayName(ChatColor.RED + args[0]);
                    player.setPlayerListName(ChatColor.RED + args[0]);
                    //player.setCustomName(args[0]);
                }
            } else if (args.length == 2)
            {
                try {
                    Player player = (Player) Bukkit.getPlayer(args[0]);

                    if (player != null)
                    {
                        player.setDisplayName(args[1]);
                        player.setPlayerListName(args[1]);
                        player.setCustomName(args[1]);
                    }
                }
                catch (Exception e)
                {
                    System.out.println("Invalid argument");
                    sender.sendMessage("Invalid player");
                }

            }
        }

        return false;
    }
}
