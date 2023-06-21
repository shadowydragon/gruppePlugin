package de.gruppe.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.nio.Buffer;

public class InvSeeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player)
        {
            if (args.length == 1)
            {
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null)
                {
                    sender.sendMessage(ChatColor.RED + "Player Not Found");
                    return true;
                }

                player.openInventory(target.getInventory());
            }
        }


        return true;
    }
}
