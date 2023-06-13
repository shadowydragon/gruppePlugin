package de.gruppe.plugin.commands;

import de.gruppe.plugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (Main.isConjoined)
        {
            Main.isConjoined = false;

        }
        else
        {
            Main.isConjoined = true;
        }

        return false;
    }
}
