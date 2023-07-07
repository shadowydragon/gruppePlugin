package de.gruppe.plugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player playerX) {
            System.out.println("can pickup: " + playerX.getCanPickupItems());

            if (args.length == 0) {
                Player player = (Player) sender;
                player.setAllowFlight(!player.getAllowFlight());

                player.sendMessage(ChatColor.GOLD + "Fly mode: " + (player.getAllowFlight() ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled"));
                return true;
            }
        }

        if (args.length == 1) {
            if (sender instanceof Player player) {

                Player targetPlayer = Bukkit.getPlayer(args[0]);
                if (targetPlayer == null) {
                    player.sendMessage(ChatColor.RED + "Player Not Found!");
                    return true;
                }
                targetPlayer.setAllowFlight(!targetPlayer.getAllowFlight());
                targetPlayer.sendMessage("Your fly mode was updated to: " + (targetPlayer.getAllowFlight() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled") + ChatColor.WHITE + " by the player: " +
                        ChatColor.YELLOW + player.getName());

                player.sendMessage("You have updated from Player: " + ChatColor.YELLOW + targetPlayer.getName() + ChatColor.WHITE + " the Flymode to: " + (targetPlayer.getAllowFlight() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
            }
        }

        return true;
    }
}
