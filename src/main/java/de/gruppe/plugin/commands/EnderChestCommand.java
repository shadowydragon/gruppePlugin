package de.gruppe.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player)
        {
            if (args.length == 0)
            {
                player.openInventory(player.getEnderChest());
            }
            else if (args.length == 1)
            {
                if (Bukkit.getPlayer(args[0]) != null)
                {
                    Player target = Bukkit.getPlayer(args[0]);

                    player.openInventory(target.getEnderChest());
                    player.sendMessage("You opened the ender cchest from: " + target.getName());
                }
            }

        }


        return true;
    }
}
