package de.gruppe.plugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            player.setInvulnerable(!player.isInvulnerable());
            player.sendMessage(ChatColor.GOLD + "Godmode:" + (player.isInvulnerable()? ChatColor.GREEN + "Enabled": ChatColor.RED + "Disabled"));
        }

        return false;
    }
}
