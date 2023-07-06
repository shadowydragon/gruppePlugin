package de.gruppe.plugin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class ChatEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println("hier");

        Player player = event.getPlayer();

        if (CoJoinControllerPlayerList.getControllerFromPlayer(player) != null) {
            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            assert controller != null;
            controller.getPlayersForController().forEach(player1 -> {
                if (player1.getUniqueId().equals(player.getUniqueId())) {

                    for (CoJoinRole coJoinRole : controller.getRolesFromPlayer(player1)) {
                        controller.getPlayerInController().put(coJoinRole, player);
                        System.out.println("reset");
                    }
                }
            });

        }
    }
}
