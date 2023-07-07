package de.gruppe.plugin.multiverse.command;

import de.gruppe.plugin.multiverse.MultiverseUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MultiverseCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player playerSender)) {
            return true;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("join")) {
                if (MultiverseUtil.woldExist(args[1])) {
                    playerSender.teleport(Objects.requireNonNull(MultiverseUtil.getWorldFromString(args[1])).getSpawnLocation());
                } else {
                    playerSender.sendMessage("The world doesn't Exist");
                }
            }

            if (args[0].equalsIgnoreCase("create")) {
                playerSender.sendMessage("World will be created");
                MultiverseUtil.generateNewWorld(args[1]);
                Bukkit.getServer().broadcastMessage(ChatColor.RED + "A new world was created with the name: " + args[1]);

            }

        }
        return false;
    }
}
