package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class CoJoinPlayerHandler implements Listener {

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        //event.getPlayer().sleep(event.getBed().getLocation(), true);

        if (CoJoinControllerPlayerList.getControllerFromPlayer(event.getPlayer()) == null) {
            return;
        }

        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(event.getPlayer());

        assert controller != null;
        for (Player player : controller.getPlayersForController()) {
            if (player != null && !player.isSleeping()) {
                player.setSleepingIgnored(true);
            }
        }
    }
}
