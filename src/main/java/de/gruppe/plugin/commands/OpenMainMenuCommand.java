package de.gruppe.plugin.commands;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.menusystem.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OpenMainMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0)
        {
            sender.sendMessage("Too much arguments");
            return true;
        }

        if (sender instanceof Player)
        {
            Player player = (Player) sender;

            new MainMenu(Main.getPlayerMenuUtility(player)).open();
        }


        return true;
    }
}
