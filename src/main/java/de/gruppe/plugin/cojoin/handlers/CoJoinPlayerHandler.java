package de.gruppe.plugin.cojoin.handlers;

import de.gruppe.plugin.cojoin.CoJoinController;
import de.gruppe.plugin.cojoin.CoJoinControllerPlayerList;
import de.gruppe.plugin.cojoin.CoJoinRole;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

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

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

        if (CoJoinControllerPlayerList.getControllerFromPlayer(event.getPlayer()) == null) {
            return;
        }

        CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(event.getPlayer());

        if (controller.playerHasRole(event.getPlayer(), CoJoinRole.MOVEMENT_WALK)) {
            controller.getPlayersForController(event.getPlayer()).forEach(player -> player.setSneaking(!player.isSneaking()));
        }

    }

    @EventHandler
    public void onEntityToggleSwim(EntityToggleSwimEvent event) {

        if (event.getEntity() instanceof Player player)
        {

            if (CoJoinControllerPlayerList.getControllerFromPlayer(player) == null) {
                return;
            }

            CoJoinController controller = CoJoinControllerPlayerList.getControllerFromPlayer(player);

            if (controller.playerHasRole(player, CoJoinRole.MOVEMENT_WALK)) {
                controller.getPlayersForController(player).forEach(player1 -> player1.setSwimming(!player1.isSwimming()));
            }

        }

    }
}
