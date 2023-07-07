package de.gruppe.plugin.commands;

import de.gruppe.plugin.Main;
import de.gruppe.plugin.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HideFromPlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            if (sender instanceof Player player) {

                if (player.canSee(Objects.requireNonNull(Bukkit.getPlayer(args[0])))) {
                    player.hidePlayer(Main.plugin, Objects.requireNonNull(Bukkit.getPlayer(args[0])));
                }
            }
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("all")) {
            Set<Player> onlinePlayers = new HashSet<>(Bukkit.getServer().getOnlinePlayers());

            Player player = (Player) sender;

            Util.hidePlayersFromPlayer(player, onlinePlayers);
        }

        return false;
    }
}
