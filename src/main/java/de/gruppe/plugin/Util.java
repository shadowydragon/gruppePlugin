package de.gruppe.plugin;

import org.bukkit.entity.Player;

import java.util.Set;

public class Util {

    //Set that a player can't see the set of player
    //Player is who cant see the ones out from the set
    public static void hidePlayersFromPlayer(Player player, Set<Player> toHidePlayers) {

        for (Player toHidePlayer : toHidePlayers) {

            if (toHidePlayer == null) {
                continue;
            }
            toHidePlayer.setCollidable(false);

            player.hidePlayer(Main.plugin, toHidePlayer);
        }

    }

    //Set that a player can see the set of players
    //player is who can see the ones out from the set
    public static void showPlayersToPlayer(Player player, Set<Player> toShowPlayers) {
        for (Player toShowPlayer : toShowPlayers) {
            player.showPlayer(Main.plugin, toShowPlayer);
        }
    }
}
