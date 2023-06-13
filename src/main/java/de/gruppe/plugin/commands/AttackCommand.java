package de.gruppe.plugin.commands;

import de.gruppe.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AttackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 1)
        {
            Player player = Bukkit.getPlayer(strings[0]);

            if (player != null)
            {
                Main.attackController = player.getUniqueId();

                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've assigned &f" + player.getName() +
                        " &eas the &fattack &econtroller."));

                if (commandSender instanceof Player && !((Player) commandSender).getName().equals(strings[0]))
                {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &eYou've been assigned the &fattack &econtroller."));
                }
            }
            else
            {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cPlayer not found!"));
            }
        }
        else
        {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5Lucien&l&dAI&r&8] &cInvalid arguments!"));
        }

        return false;
    }

}
