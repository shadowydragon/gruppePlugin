package de.gruppe.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            player.setInvisible(!player.isInvisible());
            player.sendMessage(ChatColor.GOLD + "Vanish: " + (player.isInvisible() ? ChatColor.GREEN + "Enabled" : ChatColor.RED + "Disabled"));
        }

        return false;
    }
}
