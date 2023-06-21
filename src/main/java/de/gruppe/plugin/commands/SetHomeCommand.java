package de.gruppe.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0)
        {
            sender.sendMessage("You must give a name for the home");
            return true;
        }

        if (args.length == 1)
        {
            if (sender instanceof Player)
            {
                Player player = (Player) sender;

            }
        }



        return true;
    }
}
